package org.example;

import com.github.javakeyring.PasswordAccessException;
import org.example.EncodeDecode.DataStorage;
import org.example.EncodeDecode.TYPE;

public class AuthorizationInfo
{
    private final DataStorage dataStorage;

    public AuthorizationInfo(DataStorage dataStorage)
    {
        this.dataStorage = dataStorage;
    }

    public boolean isAuthorized()
    {
        try
        {
            dataStorage.getStringData(TYPE.AuthorizedFlag);
        }
        catch (PasswordAccessException e)
        {
            return false;
        }

        return  true;
    }

    public void Authorized()
    {
        try
        {
            dataStorage.saveStringData("Authorized", TYPE.AuthorizedFlag);
        }
        catch (PasswordAccessException e)
        {
            System.out.println(e.getMessage());
            throw new RuntimeException();
        }
    }
}