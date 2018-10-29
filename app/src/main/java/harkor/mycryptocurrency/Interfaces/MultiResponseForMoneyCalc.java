package harkor.mycryptocurrency.Interfaces;

import java.util.Collection;
import java.util.Map;

import harkor.mycryptocurrency.model.CryptoPrices;

public interface MultiResponseForMoneyCalc {
    void giveResponseToCalc(Map<String,CryptoPrices> pricesMap);
}
