import org.example.Cashpoint;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CashpointTest
{
    @Test
    public void AddBanknote_SingleBanknote_ShouldIncrementTotal()
    {
        Cashpoint cashpoint = new Cashpoint();
        cashpoint.addBanknote(5);

        Assertions.assertEquals(5, cashpoint.getTotal(),
                "Добавление единственной банкноты не было произведено");
    }

    @Test
    public void AddBanknote_MultipleBanknote_ShouldIncrementTotal()
    {
        Cashpoint cashpoint = new Cashpoint();

        cashpoint.addBanknote(5);
        cashpoint.addBanknote(10);

        Assertions.assertEquals(15, cashpoint.getTotal(),
                "Добавление второй банкноты не было произведено");
    }

    @Test
    public void AddBanknote_FiveBanknotes_ShouldIncrementTotal()
    {
        Cashpoint cashpoint = new Cashpoint();
        cashpoint.addBanknote(20, 5);

        Assertions.assertEquals(100, cashpoint.getTotal(),
                "Добавление 5 одинаковых банкнот не было произведено");
    }

    @Test
    public void RemoveBanknote_CashpointIsEmpty_ShouldPreserveTotal()
    {
        Cashpoint cashpoint = new Cashpoint();
        cashpoint.removeBanknote(1);

        Assertions.assertEquals(0, cashpoint.getTotal());
    }

    @Test
    public void RemoveBanknote_UnknownBanknote_ShouldPreserveTotal()
    {
        Cashpoint cashpoint = new Cashpoint();

        cashpoint.addBanknote(5);
        cashpoint.addBanknote(5);
        cashpoint.removeBanknote(10);

        Assertions.assertEquals(10, cashpoint.getTotal());
    }

    @Test
    public void RemoveBanknote_ExistingBanknote_ShouldDecrementTotal()
    {
        Cashpoint cashpoint = new Cashpoint();

        cashpoint.addBanknote(5);
        cashpoint.addBanknote(5);
        cashpoint.removeBanknote(5);

        Assertions.assertEquals(5, cashpoint.getTotal());
    }

    @Test
    public void RemoveBanknote_ExistingThreeBanknote_ShouldDecrementTotal()
    {
        Cashpoint cashpoint = new Cashpoint();
        cashpoint.addBanknote(20, 5);
        cashpoint.removeBanknote(20, 3);

        Assertions.assertEquals(40, cashpoint.getTotal());
    }

    @Test
    public void Total_InitialState_ShouldBeZero()
    {
        Cashpoint cashpoint = new Cashpoint();
        Assertions.assertEquals(0, cashpoint.getTotal(), "При инициализации банкомат не пустой");
    }

    @Test
    public void CanGrant_SumIsZero_ShouldGrant()
    {
        Cashpoint cashpoint = new Cashpoint();
        Assertions.assertTrue(cashpoint.canGranted(0));

        cashpoint.addBanknote(5);
        Assertions.assertTrue(cashpoint.canGranted(0));
    }

    @Test
    public void CanGrant_SumEqualsToSingleBanknote_ShouldGrant()
    {
        Cashpoint cashpoint = new Cashpoint();
        cashpoint.addBanknote(5);

        Assertions.assertTrue(cashpoint.canGranted(5));
    }

    @Test
    public void CanGrant_SumNotEqualToSingleBanknote_ShouldNotGrant()
    {
        Cashpoint cashpoint = new Cashpoint();
        cashpoint.addBanknote(5);

        Assertions.assertFalse(cashpoint.canGranted(4));
    }

    @Test
    public void CanGrant_SumEqualsToBanknotesTotal_ShouldGrant()
    {
        Cashpoint cashpoint = new Cashpoint();
        cashpoint.addBanknote(5);
        cashpoint.addBanknote(5);

        Assertions.assertTrue(cashpoint.canGranted(10));
    }

    @Test
    public void CanGrant_MultipleBanknotesIntermediateValues_ShouldNotGrant()
    {
        Cashpoint cashpoint = new Cashpoint();
        cashpoint.addBanknote(5);
        cashpoint.addBanknote(5);

        Assertions.assertFalse(cashpoint.canGranted(6));
    }
}
