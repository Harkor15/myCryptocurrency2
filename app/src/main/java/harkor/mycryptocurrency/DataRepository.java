package harkor.mycryptocurrency;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

public class DataRepository {
    private  static DataRepository instance;

    public static DataRepository getInstance(){
        if(instance==null){
            instance=new DataRepository();
        }
        return instance;
    }

    public MutableLiveData<String> getAmount(){
        final MutableLiveData<String> amount=new MutableLiveData<>();
        amount.setValue("$1000000");
        return amount;
    }


}
