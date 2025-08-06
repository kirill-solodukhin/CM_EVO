package org.example.EncodeDecode;

import com.github.javakeyring.PasswordAccessException;
import org.example.AuthorizationInfo;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

public class Password
{
    public  static String hashingPassword(DataStorage dataStorage, String password) throws PasswordAccessException
    {
       try
       {
           MessageDigest digester = MessageDigest.getInstance("SHA-512");
           AuthorizationInfo authorizationInfo = new AuthorizationInfo(dataStorage);
           boolean authorizedFlag = authorizationInfo.isAuthorized();

           byte[] salt = new byte[16];
           byte[] bytePassword = password.getBytes();
           byte[] digest = digester.digest(bytePassword); // хэш

           if(authorizedFlag)
           {
               salt = dataStorage.getStringData(TYPE.SALT).getBytes(StandardCharsets.UTF_8);
           }

           if(!authorizedFlag)
           {
               SecureRandom.getInstanceStrong().nextBytes(salt);
               dataStorage.saveStringData(Arrays.toString(salt), TYPE.SALT);
           }

           digester.update(salt);

           return Arrays.toString(digest);
       }
       catch (NoSuchAlgorithmException e)
       {
           throw new RuntimeException("Не должно быть: { При хэшировании пароля }" + e.getMessage());
       }
    }
}