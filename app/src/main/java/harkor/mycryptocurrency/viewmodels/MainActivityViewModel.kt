package harkor.mycryptocurrency.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import harkor.mycryptocurrency.DataRepository

class MainActivityViewModel : ViewModel() {

    private var amount: MutableLiveData<String>? = null
    private var dataRepository: DataRepository? = null

    fun queryRepo() {
        dataRepository = DataRepository.getInstance()
        amount = dataRepository!!.amount

    }

    fun getAmount(): LiveData<String> {
        amount = MutableLiveData()
        amount!!.setValue("$1000000")
        return amount as MutableLiveData<String>
    }
}
