package org.example.EncodeDecode;

import com.github.javakeyring.PasswordAccessException;
import org.example.AuthorizationInfo;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

public class PasswordModifier
{
    private byte[] salt = new byte[16];
    private final DataStorage dataStorage;

    public PasswordModifier(DataStorage dataStorage)
    {
        this.dataStorage = dataStorage;
    }

    public void savePassword(String password) throws PasswordAccessException
    {
        generateSalt(); // new salt
        password = hashingPassword(password);
        dataStorage.saveStringData(password, TYPE.PASSWORD);
    }

    public boolean checkPassword(String Password)
    {
        // old salt
        try
        {
            salt = getSalt();

            String savingHash = dataStorage.getStringData(TYPE.PASSWORD);
            Password = hashingPassword(Password);

            return savingHash.equals(Password);
        }
        catch (PasswordAccessException e)
        {
            throw new RuntimeException("Не должно быть проблем с доступом к паролю: " + e);
        }
    }

    private String hashingPassword(String password)
    {
        try
        {
            MessageDigest digester = MessageDigest.getInstance("SHA-512");
            digester.update(salt);      // Добавляем соль

            byte[] bytePassword = password.getBytes();     // Пароль, который хэшируем
            byte[] digest = digester.digest(bytePassword); // хэш


            return DatatypeConverter.printHexBinary(digest);
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new RuntimeException("Не должно быть: { При хэшировании пароля }" + e.getMessage());
        }
    }

    private byte[] getSalt() throws PasswordAccessException
    {
        if(!Arrays.equals(salt, new byte[16])) // Если соль уже есть т.е. не равна [0, 0, ...., 0]
        {
            return salt;
        }

        String saltStr = dataStorage.getStringData(TYPE.SALT);
        String[] strings = saltStr.substring(1, saltStr.length() - 1).split(",");
        byte[] savingSalt = new byte[16];

        for(int i = 0; i < 16; i++)
        {
            savingSalt[i] = Byte.parseByte(strings[i].trim());
        }

        return savingSalt;
    }

    private void generateSalt()
    {
        try
        {
            dataStorage.deleteStringData(TYPE.SALT);
        }
        catch (PasswordAccessException ignored)
        {
        }

        try
        {
            salt = new byte[16];
            SecureRandom.getInstanceStrong().nextBytes(salt);
            dataStorage.saveStringData(Arrays.toString(salt), TYPE.SALT);
        }
        catch (PasswordAccessException e)
        {
            throw new RuntimeException("An attempt to get data that does not exist in " +
                    "{ generateSalt after delete old salt }: " + e);
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new RuntimeException(e);
        }
    }
}