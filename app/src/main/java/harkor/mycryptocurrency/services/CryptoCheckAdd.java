package harkor.mycryptocurrency.services;

import android.content.Context;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

import harkor.mycryptocurrency.CryptoAdd;
import harkor.mycryptocurrency.model.CryptoPrices;
import harkor.mycryptocurrency.model.ListRefresh;

public class CryptoCheckAdd {
    String name;
    double amount;
    CryptoAdd cryptoAdd;
    ListRefresh listRefresh;

    public CryptoCheckAdd(String name, double amount, CryptoAdd cryptoAdd,ListRefresh listRefresh) {
        this.name = name;
        this.amount = amount;
        this.cryptoAdd =cryptoAdd;
        this.listRefresh=listRefresh;
    }

    public void check(){
        RetrofitInterface retrofitInterface= new RetrofitInterface();
        retrofitInterface.singleCrypto(this);
    }
    public void add(CryptoPrices cryptoPrices){
        if(cryptoPrices.getPriceUSD()==null){
            Log.d("MyCrypto","Bad name");
            //TODO: BAD NAME ERROR
        }else{
            Log.d("MyCrypto", "Add! "+name+" "+cryptoPrices.getPriceUSD());
            Date date =new Date();
            SimpleDateFormat df=new SimpleDateFormat("yyyy.MM.dd");
            cryptoAdd.dbAddNewCrypto(name,amount,df.format(date),cryptoPrices.getPriceUSD(),
                    cryptoPrices.getPriceEUR(),cryptoPrices.getPricePLN(),cryptoPrices.getPriceBTC());
            listRefresh.refresh();

        }

    }
    public void retrofitError(Throwable t){
        Log.d("MyCrypto","Thorwable "+t.toString());
        //TODO: CONNECTION ERROR
    }


}
