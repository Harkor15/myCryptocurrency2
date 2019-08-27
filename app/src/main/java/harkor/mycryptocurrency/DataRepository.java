package harkor.mycryptocurrency;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

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
