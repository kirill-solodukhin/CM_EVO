import org.example.Cashpoint;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CashpointTest
{
    @Test
    public void addBanknote_SingleBanknote_ShouldIncrementTotal()
    {
        Cashpoint cashpoint = new Cashpoint();
        cashpoint.addBanknote(5);

        Assertions.assertEquals(5, cashpoint.getTotal(),
                "Добавление единственной банкноты не было произведено");
    }

    @Test
    public void addBanknote_MultipleBanknote_ShouldIncrementTotal()
    {
        Cashpoint cashpoint = new Cashpoint();

        cashpoint.addBanknote(5);
        cashpoint.addBanknote(10);

        Assertions.assertEquals(15, cashpoint.getTotal(),
                "Добавление второй банкноты не было произведено");
    }

    @Test
    public void addBanknote_FiveBanknotes_ShouldIncrementTotal()
    {
        Cashpoint cashpoint = new Cashpoint();
        cashpoint.addBanknote(20, 5);

        Assertions.assertEquals(100, cashpoint.getTotal(),
                "Добавление 5 одинаковых банкнот не было произведено");
    }

    @Test
    public void removeBanknote_CashpointIsEmpty_ShouldPreserveTotal()
    {
        Cashpoint cashpoint = new Cashpoint();
        cashpoint.removeBanknote(1);

        Assertions.assertEquals(0, cashpoint.getTotal());
    }

    @Test
    public void removeBanknote_UnknownBanknote_ShouldPreserveTotal()
    {
        Cashpoint cashpoint = new Cashpoint();

        cashpoint.addBanknote(5);
        cashpoint.addBanknote(5);
        cashpoint.removeBanknote(10);

        Assertions.assertEquals(10, cashpoint.getTotal());
    }

    @Test
    public void removeBanknote_ExistingBanknote_ShouldDecrementTotal()
    {
        Cashpoint cashpoint = new Cashpoint();

        cashpoint.addBanknote(5);
        cashpoint.addBanknote(5);
        cashpoint.removeBanknote(5);

        Assertions.assertEquals(5, cashpoint.getTotal());
    }

    @Test
    public void removeBanknote_ExistingThreeBanknote_ShouldDecrementTotal()
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

    @Test
    public void Regression6()
    {
        var cashpoint = new Cashpoint();

        cashpoint.addBanknote(5, 1);
        cashpoint.addBanknote(2, 1);
        cashpoint.addBanknote(3, 1);

        cashpoint.removeBanknote(5, 1);

        AssertCanGrantOnly(cashpoint, List.of(2, 3, 5));

        cashpoint.removeBanknote(3, 1);

        AssertCanGrantOnly(cashpoint, List.of(2));
    }


    @Test
    public void CanGranted_Regression7()
     {
        Cashpoint cashpoint = new Cashpoint();

        cashpoint.addBanknote(5, 1); // 5 -- first case

        cashpoint.addBanknote(2, 1);
        cashpoint.addBanknote(3, 1); // 2 + 3 = 5 -- second case

        cashpoint.addBanknote(1, 1);
        cashpoint.addBanknote(4, 1); // 1 + 4 = 5 -- third case

        cashpoint.removeBanknote(1, 1); // remove 1 + 4 case
        cashpoint.removeBanknote(2, 1); // remove 2 + 3 case
        cashpoint.removeBanknote(5, 1); // remove 5 case

        Assertions.assertFalse(cashpoint.canGranted(5), "Банкомат смог выдать не существующую сумму");

    }

    private void AssertCanGrantOnly(Cashpoint cashpoint, List<Integer> exactValues)
    {
        for (int i = Collections.min(exactValues); i <= Collections.max(exactValues) ; i++)
        {
            boolean expectedCanGrant = exactValues.contains(i);
            Assertions.assertEquals(expectedCanGrant, cashpoint.canGranted(i));
        }
    }

}
