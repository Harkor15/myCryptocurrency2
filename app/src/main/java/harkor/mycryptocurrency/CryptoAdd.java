package harkor.mycryptocurrency;

public interface CryptoAdd {
    void dbAddNewCrypto(String tag, double amount, String date, double priceUsd,double priceEur,double pricePln, double priceBtc);

}
