package harkor.mycryptocurrency;

import java.util.LinkedList;
import java.util.Map;

import harkor.mycryptocurrency.model.CryptoPrices;
import harkor.mycryptocurrency.model.Cryptocurrency;
import harkor.mycryptocurrency.services.DatabaseController;
import harkor.mycryptocurrency.services.RetrofitInterface;
import harkor.mycryptocurrency.view.OverallPrice;

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
        smallTable=db.smallTable();
        currencyTag=overallPrice.getCurrencyId();
        RetrofitInterface retrofitInterface=new RetrofitInterface();
        if(smallTable.size()!=0){
            String names="";
            for(int i=0;i<smallTable.size();i++){
                names+=smallTable.get(i).tag+",";
            }
            retrofitInterface.multiCrypto(names,this);
        }else{
            overallPrice.setOverallPrice(addCurrencyTag("0"));
        }

    }

    @Override
    public void giveResponseToCalc( Map<String,CryptoPrices> pricesMap) {
        //Log.d("MyCryptocurrency", pricesMap.get("LSK").getPricePLN().toString());
        double sum=0;
        for(int i=0;i<smallTable.size();i++){
            CryptoPrices cryptoPrices=pricesMap.get(smallTable.get(i).tag);
            switch (currencyTag){
                case 1:
                    sum+=cryptoPrices.getPriceUSD()*smallTable.get(i).amount;
                    break;
                case 2:
                    sum+=cryptoPrices.getPriceEUR() *smallTable.get(i).amount;
                    break;
                case 3:
                    sum+=
                            cryptoPrices.getPricePLN()
                            *smallTable.get(i).amount;
                    break;
                default:
                    sum+=cryptoPrices.getPriceBTC()*smallTable.get(i).amount;
                    break;
            }
        }
        String sumTxt=doubleFormat.format(sum);
        sumTxt=addCurrencyTag(sumTxt);
        overallPrice.setOverallPrice(sumTxt); //TODO: add currency tag!!!
    }
    private String addCurrencyTag(String amount){
        String result="";
        switch (currencyTag){
            case 1: result="$"+amount;break;
            case 2: result=amount+"€";break;
            case 3: result=amount+"zł";break;
            case 4: result=amount+" BTC";
        }
        return result;
    }
}
