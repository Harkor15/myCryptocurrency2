package harkor.mycryptocurrency.viewmodel;

import android.widget.AdapterView;

import harkor.mycryptocurrency.ListViewAdapter;
import harkor.mycryptocurrency.MoneyCalc;
import harkor.mycryptocurrency.view.DialogAdd;
import harkor.mycryptocurrency.view.DialogSettings;
import harkor.mycryptocurrency.view.InterfaceOfMainActivity;
import harkor.mycryptocurrency.view.OverallPrice;

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

}
