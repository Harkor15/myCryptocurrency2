package harkor.mycryptocurrency.viewmodels

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import harkor.mycryptocurrency.CoinpaprikaApi

import harkor.mycryptocurrency.DataRepository
import harkor.mycryptocurrency.ListDataRepository
import harkor.mycryptocurrency.RetrofitClientInstance

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private var amount: MutableLiveData<String>? = null
    private var dataRepository: DataRepository? = null
    var dataFlag= MutableLiveData<Boolean>()

    fun queryRepo() {
        dataRepository = DataRepository.getInstance()
        amount = dataRepository!!.amount

    }

    fun getAmount(): LiveData<String> {
        amount = MutableLiveData()
        amount!!.setValue("$1000000")
        return amount as MutableLiveData<String>
    }

    fun isDataDownloaded(): MutableLiveData<Boolean> {
        val dbFile=getApplication<Application>().getDatabasePath("Cryptocurrency.db")
        dataFlag.value=dbFile.exists()
        if(dataFlag.value==false){
            //ListDataRepository
          //  getDataListFromApi()
        }
        getDataListFromApi()//TODO: DELETE!
        return dataFlag
    }
    fun getDataListFromApi(){
        val retrofit = RetrofitClientInstance.instance.create(CoinpaprikaApi::class.java)

        retrofit.getCoinsDataList().observeForever { dataList ->
            Log.d("MyCrypto", dataList.toString())
        }
    }
    //val dataListObserver
}
