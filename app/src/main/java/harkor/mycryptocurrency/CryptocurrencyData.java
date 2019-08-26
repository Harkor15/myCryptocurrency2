package harkor.mycryptocurrency;

public class CryptocurrencyData {
        public int id;
        public String tag;
        public String name;
        public double amount;
        public String date;
        public double priceUsd;
        public double priceEur;
        public double pricePln;
        public double priceBtc;


    public void setId(int id) {
        this.id = id;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPriceUsd(double priceUsd) {
        this.priceUsd = priceUsd;
    }

    public void setPriceEur(double priceEur) {
        this.priceEur = priceEur;
    }

    public void setPricePln(double pricePln) {
        this.pricePln = pricePln;
    }

    public void setPriceBtc(double priceBtc) {
        this.priceBtc = priceBtc;
    }

    public int getId() {
        return id;
    }

    public String getTag() {
        return tag;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public double getPriceUsd() {
        return priceUsd;
    }

    public double getPriceEur() {
        return priceEur;
    }

    public double getPricePln() {
        return pricePln;
    }

    public double getPriceBtc() {
        return priceBtc;
    }
}
