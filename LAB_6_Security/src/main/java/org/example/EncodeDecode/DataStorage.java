package org.example.EncodeDecode;

import com.github.javakeyring.BackendNotSupportedException;
import com.github.javakeyring.Keyring;
import com.github.javakeyring.PasswordAccessException;

public class DataStorage
{
    private final Keyring dataStorage;

    public DataStorage() throws BackendNotSupportedException
    {
        this.dataStorage = Keyring.create();
    }

    public void saveStringData(String data, TYPE type) throws PasswordAccessException
    {
        dataStorage.setPassword("SECURITY", type.name(), data);
    }

    public String getStringData(TYPE type) throws PasswordAccessException
    {
        return dataStorage.getPassword("SECURITY", type.name());
    }

    public void deleteStringData(TYPE type) throws PasswordAccessException
    {
        dataStorage.deletePassword("SECURITY", type.name());
    }
}
