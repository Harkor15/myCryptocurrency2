package harkor.mycryptocurrency;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AddCurrencySign {
    @Test
    public void addingSignTest(){
        assertEquals("2€", harkor.mycryptocurrency.services.AddCurrencySign.addCurrencySign(2,"2"));
        assertEquals("$100", harkor.mycryptocurrency.services.AddCurrencySign.addCurrencySign(1,"100"));
        assertEquals("1000zł", harkor.mycryptocurrency.services.AddCurrencySign.addCurrencySign(3,"1000"));
        assertEquals("1 BTC", harkor.mycryptocurrency.services.AddCurrencySign.addCurrencySign(4,"1"));
    }
}
