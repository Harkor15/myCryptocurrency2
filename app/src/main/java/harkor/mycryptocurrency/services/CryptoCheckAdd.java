package harkor.mycryptocurrency.services;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

import harkor.mycryptocurrency.Interfaces.CryptoAdd;
import harkor.mycryptocurrency.Interfaces.ToastDisplay;
import harkor.mycryptocurrency.model.CryptoPrices;
import harkor.mycryptocurrency.Interfaces.ListRefresh;

public class CryptoCheckAdd {
    String name;
    double amount;
    CryptoAdd cryptoAdd;
    ListRefresh listRefresh;
    ToastDisplay toastDisplay;

    public CryptoCheckAdd(String name, double amount, CryptoAdd cryptoAdd,ListRefresh listRefresh,ToastDisplay toastDisplay) {
        this.name = name;
        this.amount = amount;
        this.cryptoAdd =cryptoAdd;
        this.listRefresh=listRefresh;
        this.toastDisplay=toastDisplay;
    }

    public void check(){
        RetrofitInterface retrofitInterface= new RetrofitInterface();
        retrofitInterface.singleCrypto(this);
    }

    public void add(CryptoPrices cryptoPrices){
        if(cryptoPrices.getPriceUSD()==null){
            toastDisplay.showToast(1);
        }else{
            Date date =new Date();
            SimpleDateFormat df=new SimpleDateFormat("yyyy.MM.dd");
            cryptoAdd.dbAddNewCrypto(name,amount,df.format(date),cryptoPrices.getPriceUSD(),
                    cryptoPrices.getPriceEUR(),cryptoPrices.getPricePLN(),cryptoPrices.getPriceBTC());
            listRefresh.refresh();
        }
    }

    public void retrofitError(Throwable t){
        toastDisplay.showToast(2);
    }
}
