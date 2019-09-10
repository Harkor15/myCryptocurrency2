package harkor.mycryptocurrency.viewmodels

import android.app.Application
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import harkor.mycryptocurrency.AppDataListDatabase
import harkor.mycryptocurrency.CryptoDataClassEntity
import harkor.mycryptocurrency.CryptocurrencyInfo
import harkor.mycryptocurrency.RetrofitInstance
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivityViewModel(application: Application) : AndroidViewModel(application) {
    private var amount: MutableLiveData<String>? = null
    var dataFlag= MutableLiveData<Boolean>()
    private var compositeDisposable: CompositeDisposable?=null

    private val TAG= "MyCrypto"

    fun getAmount(): LiveData<String> {
        amount = MutableLiveData()
        amount!!.setValue("$1000000")
        return amount as MutableLiveData<String>
    }

    fun isDataDownloaded(): MutableLiveData<Boolean> {
        val dbFile=getApplication<Application>().getDatabasePath("CryptoDataClassEntity.db")
        dataFlag.value=dbFile.exists()
        if(dataFlag.value==false){
            getDataListFromApi()
        }
        return dataFlag
    }

    fun getDataListFromApi(){
        Log.d(TAG, "add observer")
        compositeDisposable= CompositeDisposable()
        compositeDisposable?.add(RetrofitInstance.requestInterface.getListData()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse,this::handleError)
        )
    }

    private fun handleResponse(dataList: List<CryptoDataClassEntity>){
        Log.d("MyCrypto",dataList.size.toString()) //TODO: Delete!
        val db=Room.databaseBuilder(
                getApplication<Application>().applicationContext,
                AppDataListDatabase::class.java,"CryptoDataClassEntity.db"
        ).build()
        GlobalScope.launch {
            db.cryptoDataListDao().insertDataList(dataList)
            withContext(Dispatchers.Main){dataFlag.value=true}
        }
    }

    private fun handleError(error: Throwable){
        Log.d("MyCrypto", "Error: ${error.message} , ${error.cause}")
        Toast.makeText(getApplication<Application>().applicationContext,"Check your internet connection", Toast.LENGTH_SHORT).show() //TODO: Make string resource
        Handler().postDelayed({
            Log.d(TAG, "Try again!")
            getDataListFromApi()
        }, 5000)
    }

    fun getAllPrice(){
        RetrofitInstance.requestInterface.getAllCryptoPrices()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::allPricecsHandleSucces,this::allPricesHandleError)


    }
    private fun allPricecsHandleSucces(priceData:CryptocurrencyInfo){
        //Log.d(TAG,"Succes response amount: ${priceData.size}")
        Log.d(TAG,"First record: ${priceData}")

    }
    private fun allPricesHandleError(error: Throwable){
        Log.d(TAG,"ERROR Retrofit: $error")
    }


}
