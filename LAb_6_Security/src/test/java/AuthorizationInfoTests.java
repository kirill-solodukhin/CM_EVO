import com.github.javakeyring.BackendNotSupportedException;
import com.github.javakeyring.PasswordAccessException;
import org.example.AuthorizationInfo;
import org.example.EncodeDecode.DataStorage;
import org.example.EncodeDecode.TYPE;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

public class AuthorizationInfoTests
{
    @Mock
    DataStorage dataStorage;

    public AuthorizationInfoTests() throws BackendNotSupportedException
    {
    }

    @Test
    @ExtendWith(MockitoExtension.class)
    public void isAuthorized_beforeAuthorized_shouldReturnFalse() throws PasswordAccessException
    {
        AuthorizationInfo authorizationInfo = new AuthorizationInfo(dataStorage);
        Mockito.when(dataStorage.getStringData(TYPE.AuthorizedFlag)).thenThrow(PasswordAccessException.class);

        Assertions.assertFalse(authorizationInfo.isAuthorized());
    }

    @Test
    @ExtendWith(MockitoExtension.class)
    public void isAuthorized_afterAuthorized_shouldReturnTrue() throws PasswordAccessException
    {
        AuthorizationInfo authorizationInfo = new AuthorizationInfo(dataStorage);
        Mockito.when(dataStorage.getStringData(TYPE.AuthorizedFlag)).thenReturn("any");

        Assertions.assertTrue(authorizationInfo.isAuthorized());
    }
}
