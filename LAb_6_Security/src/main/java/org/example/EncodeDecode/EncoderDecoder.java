package org.example.EncodeDecode;

import com.github.javakeyring.PasswordAccessException;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.xml.bind.DatatypeConverter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;


public class EncoderDecoder
{
    private static Key KEY;
    private static IvParameterSpec IV;
    private static final String transformation = "AES/CBC/PKCS5Padding";

    private final KeyStore keyStore;
    private final static char[] KEY_OF_KEY_STORE = "kirillPassword".toCharArray();

    private final static String CERTIFICATES_ALIAS = "My certificates";
    private final static String KEY_ALIAS = "Key of Cipher";
    private final static char[] KEY_PASSWORD = "MEDVED".toCharArray();
    ;

    private final DataStorage dataStorage;

    public EncoderDecoder(DataStorage dataStorage)
    {
        this.dataStorage = dataStorage;

        try
        {
            keyStore = KeyStore.getInstance("PKCS12"); // Создали хранилище с типом PKCS12
        }
        catch (KeyStoreException e)
        {
            throw new RuntimeException(e);
        }

        getKey();
        getInitializationVector();
    }

    // кодирование
    public String encode(String text) throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
    {
        Cipher cipher = Cipher.getInstance(transformation);
        cipher.init(Cipher.ENCRYPT_MODE, KEY, IV);
        byte[] encodedByte = cipher.doFinal(text.getBytes());

        return DatatypeConverter.printHexBinary(encodedByte);
    }

    // декодирование
    public String decode(String text) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
    {
        Cipher cipher = Cipher.getInstance(transformation);
        cipher.init(Cipher.DECRYPT_MODE, KEY, IV);
        return new String(cipher.doFinal(DatatypeConverter.parseHexBinary(text)));
    }

    private void getKey()
    {
        if (KEY != null)
        {
            return;
        }

        try (FileInputStream fis = new FileInputStream("keystore.p12"))
        {
            keyStore.load(fis, KEY_OF_KEY_STORE);
            KEY = keyStore.getKey(KEY_ALIAS, KEY_PASSWORD);
        }
        catch (IOException e)
        {
            generateKey();
        }
        catch (CertificateException | NoSuchAlgorithmException e)
        {
            throw new RuntimeException(e + "Странная проблема");
        }
        catch (UnrecoverableKeyException | KeyStoreException e)
        {
            throw new RuntimeException(e + "Ошибка извлечения ключа");
        }
    }

    private void generateKey() {
        try (FileOutputStream fos = new FileOutputStream("keystore.p12")) {
            keyStore.load(null, KEY_OF_KEY_STORE); // Инициализировали хранилище пустым

            SecretKey secretKey = KeyGenerator.getInstance("AES").generateKey(); // Сгенерировали ключ
            Certificate[] certificates = keyStore.getCertificateChain(CERTIFICATES_ALIAS); // Генерация сертификатов My certificates - имя доступа к записи

            keyStore.setKeyEntry(
                    KEY_ALIAS,      // Имя доступа
                    secretKey,      // ключ
                    KEY_PASSWORD,   // пароль ключа
                    certificates    // цепочка сертификатов
            );

            keyStore.store(fos, KEY_OF_KEY_STORE);
        } catch (NoSuchAlgorithmException ignored) {

        } catch (CertificateException | KeyStoreException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void getInitializationVector() {
        if (IV != null) {
            return;
        }

        try {
            String strIV = dataStorage.getStringData(TYPE.IV);
            IV = strToIv(strIV);
        } catch (PasswordAccessException e) {
            generateIV();
        }
    }

    private String IVToStr(IvParameterSpec iv) {
        byte[] bytes = iv.getIV();
        StringBuilder strIV = new StringBuilder();

        for (byte b : bytes) {
            strIV.append(b).append("/");
        }

        return strIV.toString();
    }

    private IvParameterSpec strToIv(String strIV) {
        String[] strings = strIV.split("/");
        byte[] bytes = new byte[16];
        IvParameterSpec ivParameterSpec;

        for (int i = 0; i < 16; i++) {
            bytes[i] = Byte.parseByte(strings[i]);
        }

        return new IvParameterSpec(bytes);
    }

    private void generateIV() {
        SecureRandom secureRandom = null;

        try {
            secureRandom = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Проблемы инстанцирования секюрити рандом");
        }

        byte[] rnd = new byte[16];
        secureRandom.nextBytes(rnd);

        // IV
        IV = new IvParameterSpec(rnd);

        try {
            dataStorage.saveStringData(IVToStr(IV), TYPE.IV);
        } catch (PasswordAccessException e) {
            throw new RuntimeException(e);
        }
    }

}

// IllegalArgumentException - ошибка если данные для парсинга изменены