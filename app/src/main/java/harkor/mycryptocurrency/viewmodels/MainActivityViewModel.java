package harkor.mycryptocurrency.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.provider.ContactsContract;

import harkor.mycryptocurrency.DataRepository;

public class MainActivityViewModel extends ViewModel {

    private MutableLiveData<String> amount;
    private DataRepository dataRepository;

    public void queryRepo(){
        dataRepository= DataRepository.getInstance();
        amount=dataRepository.getAmount();

    }
    public LiveData<String> getAmount() {
        amount=new MutableLiveData<>();
        amount.setValue("$1000000");
        return amount;
    }
}
