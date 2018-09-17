package harkor.mycryptocurrency.model;

import java.util.LinkedList;

public class Cryptocurrency {
    int id;
    String tag;
    double amount;
    String date;
    double priceUsd;
    double priceEur;
    double pricePln;

    public Cryptocurrency(int id, String tag, double amount, String date, double priceUsd, double priceEur, double pricePln) {
        this.id = id;
        this.tag = tag;
        this.amount = amount;
        this.date = date;
        this.priceUsd = priceUsd;
        this.priceEur = priceEur;
        this.pricePln = pricePln;
    }

    public Cryptocurrency(int id, String tag, double amount) {
        this.id = id;
        this.tag = tag;
        this.amount = amount;
    }
}
