package harkor.mycryptocurrency.viewmodel;

import android.util.Log;

import harkor.mycryptocurrency.SingleResponseForAdvanced;
import harkor.mycryptocurrency.model.Cryptocurrency;
import harkor.mycryptocurrency.services.RetrofitInterface;
import harkor.mycryptocurrency.view.AdvancedDialogInterface;

public class AdvancedDialogViewModel implements SingleResponseForAdvanced{
    AdvancedDialogInterface adi;
    int cryptoCode;
    Cryptocurrency cryptocurrency;
    java.text.DecimalFormat doubleFormat=new java.text.DecimalFormat("0.00");

    public AdvancedDialogViewModel(AdvancedDialogInterface adi){
        this.adi=adi;
        cryptoCode=adi.getCurrencyCode();
        cryptocurrency=adi.getCrypto();
    }
    public void startViewModel(){
        adi.setNameText(cryptocurrency.tag);
        adi.setAmountText(cryptocurrency.amount+"");
        adi.setTimeText(cryptocurrency.date);
        adi.setAddPriceText(addCurrencyTag(addPrice()+""));
        adi.setAddValueText(addCurrencyTag((doubleFormat.format(addPrice()*cryptocurrency.amount)+"")));
        RetrofitInterface retrofitInterface=new RetrofitInterface();
        retrofitInterface.actualPrice(cryptocurrency.tag,cryptoCode,this);
    }

    private double addPrice(){
        double price=0;
        switch (cryptoCode){
            case 1:
                price=cryptocurrency.priceUsd;break;
            case 2:
                price=cryptocurrency.priceEur; break;
            case 3:
                price=cryptocurrency.pricePln; break;
            case 4:
                price=cryptocurrency.priceBtc; break;

        }

        return  price;
    }
    private String addCurrencyTag(String amount){
        String result="";
        switch (cryptoCode){
            case 1: result="$"+amount;break;
            case 2: result=amount+"€";break;
            case 3: result=amount+"zł";break;
            case 4: result=amount+" BTC";
        }
        return result;
    }

    @Override
    public void actualPrice(double price) {
        adi.setBalanceText(addCurrencyTag(doubleFormat.format((price*cryptocurrency.amount)-(addPrice()*cryptocurrency.amount)))+"");
        adi.setActualPriceText(addCurrencyTag(price+""));
        adi.setActualValueText(addCurrencyTag(doubleFormat.format(price*cryptocurrency.amount)+""));
        adi.setBalancePercentText((doubleFormat.format((((price/addPrice())*100)-100)))+"%");
    }

    @Override
    public void wentWrong() {
        Log.d("MyCrypto","no connection");
    }
}
