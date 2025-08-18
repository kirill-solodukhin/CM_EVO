import com.github.javakeyring.PasswordAccessException;
import org.example.EncodeDecode.DataStorage;
import org.example.EncodeDecode.PasswordModifier;
import org.example.EncodeDecode.TYPE;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

public class PasswordModifierTests
{
    @Mock
    DataStorage dataStorage;

    // Хеш от "password" + salt new byte[16]
    private final String hashWithSalt = "C62659937D57380280FD707B34F7C284647B085A42656EA916859AD566C172FADB940DEC2BEB34826" +
            "998C61B1A26AF0DF4C7A6382826C075AA3EAA46D40AF1E0";

    @Test
    @ExtendWith(MockitoExtension.class)
    public void checkPassword_truePasswordTrueSalt_shouldReturnTrue() throws PasswordAccessException
    {
        PasswordModifier pm = new PasswordModifier(dataStorage);

        Mockito.when(dataStorage.getStringData(TYPE.SALT)).thenReturn(Arrays.toString(new byte[16]));

        Mockito
           .when(dataStorage.getStringData(TYPE.PASSWORD))
           .thenReturn(hashWithSalt);

        Assertions.assertTrue(pm.checkPassword("password"));
    }

    @Test
    @ExtendWith(MockitoExtension.class)
    public void checkPassword_truePasswordBadSalt_shouldReturnFalse() throws PasswordAccessException
    {
        PasswordModifier pm = new PasswordModifier(dataStorage);
        Mockito.when(dataStorage.getStringData(TYPE.SALT)).thenReturn(Arrays.toString(new byte[] {
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
        }));

        Mockito
           .when(dataStorage.getStringData(TYPE.PASSWORD))
           .thenReturn(hashWithSalt);

        Assertions.assertFalse(pm.checkPassword("password"));
    }

    @Test
    @ExtendWith(MockitoExtension.class)
    public void checkPassword_badPasswordTrueSalt_shouldReturnFalse() throws PasswordAccessException
    {
        PasswordModifier pm = new PasswordModifier(dataStorage);

        Mockito.when(dataStorage.getStringData(TYPE.PASSWORD)).thenReturn(hashWithSalt);
        Mockito.when(dataStorage.getStringData(TYPE.SALT)).thenReturn(Arrays.toString(new byte[16]));

        Assertions.assertFalse(pm.checkPassword("bad password"));
    }

    @Test
    @ExtendWith(MockitoExtension.class)
    public void checkPassword_badPasswordBadSalt_shouldReturnFalse() throws PasswordAccessException
    {
        PasswordModifier pm = new PasswordModifier(dataStorage);

        Mockito.when(dataStorage.getStringData(TYPE.PASSWORD)).thenReturn("password");
        Mockito.when(dataStorage.getStringData(TYPE.SALT)).thenReturn(Arrays.toString(new byte[] {
                1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
        }));

        Assertions.assertFalse(pm.checkPassword("password"));
    }
}
