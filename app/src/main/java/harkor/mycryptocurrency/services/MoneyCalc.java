package harkor.mycryptocurrency.services;

import java.util.LinkedList;
import java.util.Map;

import harkor.mycryptocurrency.Interfaces.MultiResponseForMoneyCalc;
import harkor.mycryptocurrency.model.CryptoPrices;
import harkor.mycryptocurrency.services.Cryptocurrency;
import harkor.mycryptocurrency.services.DatabaseController;
import harkor.mycryptocurrency.services.RetrofitInterface;
import harkor.mycryptocurrency.Interfaces.OverallPrice;

public class MoneyCalc implements MultiResponseForMoneyCalc {
    DatabaseController db;
    OverallPrice overallPrice;
    LinkedList<Cryptocurrency> smallTable;
    int currencyTag;
    java.text.DecimalFormat doubleFormat=new java.text.DecimalFormat("0.00");

    public MoneyCalc(DatabaseController db, OverallPrice overallPrice) {
        this.db = db;
        this.overallPrice = overallPrice;
    }
    public void goGoGo(){
        //smallTable=db.smallTable();
        currencyTag=overallPrice.getCurrencyId();
        RetrofitInterface retrofitInterface=new RetrofitInterface();
        if(smallTable.size()!=0){
            String names="";
            for(int i=0;i<smallTable.size();i++){
               // names+=smallTable.get(i).tag+",";
            }
            retrofitInterface.multiCrypto(names,this);
        }else{
            overallPrice.setOverallPrice(AddCurrencySign.addCurrencySign(currencyTag,"0"));
        }
    }

    @Override
    public void giveResponseToCalc( Map<String,CryptoPrices> pricesMap) {/*
        double sum=0;
        for(int i=0;i<smallTable.size();i++){
            CryptoPrices cryptoPrices=pricesMap.get(smallTable.get(i).tag);
            switch (currencyTag){
                case 1:
                    sum+=cryptoPrices.getPriceUSD()*smallTable.get(i).amount;
                    break;
                case 2:
                    sum+=cryptoPrices.getPriceEUR()*smallTable.get(i).amount;
                    break;
                case 3:
                    sum+=cryptoPrices.getPricePLN()*smallTable.get(i).amount;
                    break;
                default:
                    sum+=cryptoPrices.getPriceBTC()*smallTable.get(i).amount;
                    break;
            }
        }
        String sumTxt=doubleFormat.format(sum);
        sumTxt=AddCurrencySign.addCurrencySign(currencyTag,sumTxt);
        overallPrice.setOverallPrice(sumTxt);
        */
    }
}
