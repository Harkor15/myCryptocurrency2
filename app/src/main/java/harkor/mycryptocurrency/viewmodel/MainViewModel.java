package harkor.mycryptocurrency.viewmodel;

import android.os.Bundle;

import harkor.mycryptocurrency.services.MoneyCalc;
import harkor.mycryptocurrency.view.DialogAdd;
import harkor.mycryptocurrency.view.DialogAdvanced;
import harkor.mycryptocurrency.view.DialogSettings;
import harkor.mycryptocurrency.Interfaces.InterfaceOfMainActivity;
import harkor.mycryptocurrency.Interfaces.OverallPrice;

public class MainViewModel {
    private InterfaceOfMainActivity interfaceOfMainActivity;
    public MainViewModel(InterfaceOfMainActivity interfaceOfMainActivity){
        this.interfaceOfMainActivity=interfaceOfMainActivity;
    }

    public void addCrypto(){
        DialogAdd dialogAdd=new DialogAdd();
        dialogAdd.show(interfaceOfMainActivity.fragmentMenagerGetter(), "add");
    }
    public void refresh(OverallPrice overallPrice){
        MoneyCalc moneyCalc=new MoneyCalc(interfaceOfMainActivity.databaseGetter(),overallPrice);
        moneyCalc.goGoGo();
    }
    public void settings(){
        new DialogSettings().show(interfaceOfMainActivity.fragmentMenagerGetter(),"settings");
    }
    /*public void itemClick(int i){
        Bundle bundle=new Bundle();
        bundle.putInt("idCrypto",listDataEditor.dbId(i));
        DialogAdvanced dialogAdvanced=new DialogAdvanced();
        dialogAdvanced.setArguments(bundle);
        dialogAdvanced.show(getFragmentManager(), "advanced");
    }*/
}
