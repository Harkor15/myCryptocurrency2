package harkor.mycryptocurrency.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import harkor.mycryptocurrency.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


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
       RetrofitInstance.requestInterface.getListData().observeOn(AndroidSchedulers.mainThread())
               .subscribeOn(Schedulers.io())
               .subscribe(this::handleResponse,this::handleError)

        //retrofit.getCoinsDataList().observeForever { dataList ->
        //   Log.d("MyCrypto", dataList.toString())
        //}
    }

    private fun handleResponse(dataList: List<CryptoListData>){
        Log.d("MyCrypto",dataList.size.toString())
    }
    private fun handleError(error: Throwable){
        Log.d("MyCrypto", "Error: ${error.message} , ${error.cause}")
    }

    override fun onCleared() {
        super.onCleared()
        //TODO: Clear disposable
    }


//val dataListObserver
}
