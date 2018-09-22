package harkor.mycryptocurrency.model;

import com.google.gson.annotations.SerializedName;

public class CryptoPrices {
    @SerializedName("USD")
    private Double priceUSD;
    @SerializedName("EUR")
    private Double priceEUR;
    @SerializedName("PLN")
    private Double pricePLN;
    @SerializedName("BTC")
    private Double priceBTC;

    public CryptoPrices(Double priceUSD, Double priceEUR, Double pricePLN, Double priceBTC) {
        this.priceUSD = priceUSD;
        this.priceEUR = priceEUR;
        this.pricePLN = pricePLN;
        this.priceBTC = priceBTC;
    }

    public Double getPriceUSD() {
        return priceUSD;
    }

    public Double getPriceEUR() {
        return priceEUR;
    }

    public Double getPricePLN() {
        return pricePLN;
    }

    public Double getPriceBTC() {
        return priceBTC;
    }

    public void setPriceUSD(Double priceUSD) {
        this.priceUSD = priceUSD;
    }

    public void setPriceEUR(Double priceEUR) {
        this.priceEUR = priceEUR;
    }

    public void setPricePLN(Double pricePLN) {
        this.pricePLN = pricePLN;
    }

    public void setPriceBTC(Double priceBTC) {
        this.priceBTC = priceBTC;
    }
}
