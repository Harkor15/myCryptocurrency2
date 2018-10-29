package harkor.mycryptocurrency.viewmodel;

import android.util.Log;

import harkor.mycryptocurrency.Interfaces.AddDialogInterface;
import harkor.mycryptocurrency.Interfaces.CryptoAdd;
import harkor.mycryptocurrency.services.CryptoCheckAdd;

public class AddDialogViewmodel implements CryptoAdd{
AddDialogInterface addDialogInterface;

    public AddDialogViewmodel(AddDialogInterface addDialogInterface) {
        this.addDialogInterface = addDialogInterface;
    }

    public void okClick(String name, String amountS){
     name=name.toUpperCase();
     if (!amountS.equals("")) {
         double amount;
         try{
             amount= Double.parseDouble(amountS);
             CryptoCheckAdd cryptoCheckAdd=new CryptoCheckAdd(name,amount,this,addDialogInterface.getListRefresh(),
                     addDialogInterface.getToastDisplay());
             cryptoCheckAdd.check();
         }catch (Error e){
             addDialogInterface.getToastDisplay().showToast(1);
         }
     }
 }

    @Override
    public void dbAddNewCrypto(String tag, double amount, String date, double priceUsd, double priceEur, double pricePln, double priceBtc) {
        addDialogInterface.getDatabase().addCrypto(tag,amount,date,priceUsd,priceEur,pricePln,priceBtc) ;
    }
}
