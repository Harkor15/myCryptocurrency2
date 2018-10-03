package harkor.mycryptocurrency.viewmodel;

import java.util.LinkedList;

import harkor.mycryptocurrency.model.Cryptocurrency;
import harkor.mycryptocurrency.services.DatabaseController;

public class ListDataEditor {
    //LinkedList<String> names;
    //LinkedList<Double> amounts;
    LinkedList<Cryptocurrency> fullCrypto;
    DatabaseController db;

    public ListDataEditor(DatabaseController db){
        this.db=db;
        fullCrypto=db.fullTable();
    }

    public LinkedList<String> getNames() {
        LinkedList<String> names=new LinkedList<>();
        for(int i=0;i<fullCrypto.size();i++){
            names.add(fullCrypto.get(i).tag);
        }
        return names;
    }

    public LinkedList<Double> getAmounts() {
        LinkedList<Double> amounts=new LinkedList<>();
        for(int i=0;i<fullCrypto.size();i++){
            amounts.add(fullCrypto.get(i).amount);
        }
        return amounts;
    }

    public int dbId(int number){
        return fullCrypto.get(number).id;
    }
}
