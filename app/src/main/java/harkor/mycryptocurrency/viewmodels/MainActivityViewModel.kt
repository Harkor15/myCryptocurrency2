package harkor.mycryptocurrency.viewmodels

import android.app.Application
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import harkor.mycryptocurrency.*
import harkor.mycryptocurrency.services.DatabaseController
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*


class MainActivityViewModel(application: Application) : AndroidViewModel(application) {
    private var amount: MutableLiveData<String>? = null
    var dataFlag= MutableLiveData<Boolean>()
    private var compositeDisposable: CompositeDisposable?=null
    val db=Room.databaseBuilder(
            getApplication<Application>().applicationContext,
            AppDataListDatabase::class.java,"CryptoDataClassEntity.db"
    ).build()

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
        GlobalScope.launch {
            db.cryptoDataListDao().insertDataList(dataList)
            val oldDatabaseFile=getApplication<Application>().getDatabasePath("Cryptocurrency.db")
            if(oldDatabaseFile.exists()){
               Log.d(TAG,"Migration needed!")
               migrateOldDatabase()
            }else{
                Log.d(TAG,"Migranion not needed!")
            }
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

    /*fun getAllPrice(){
        RetrofitInstance.requestInterface.getAllCryptoPrices()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::allPricecsHandleSucces,this::allPricesHandleError)


    }*/
    private fun allPricecsHandleSucces(priceData:CryptocurrencyInfo){
        //Log.d(TAG,"Succes response amount: ${priceData.size}")
        Log.d(TAG,"First record: ${priceData}")

    }
    private fun allPricesHandleError(error: Throwable){
        Log.d(TAG,"ERROR Retrofit: $error")
    }

    private fun migrateOldDatabase(){
        val oldDatabase=DatabaseController(getApplication<Application>().applicationContext)
        val allData=oldDatabase.fullTable().toList()
        for(crypto in allData){
            val dataListInfo=db.cryptoDataListDao().getCryptoFromSymbol(crypto.tag)
            if(dataListInfo!=null){//!!!
                val newOwnedCrypto=CryptocurrencyOwnedEntity(0,dataListInfo.id,dataListInfo.name,
                        dataListInfo.symbol,crypto.amount,crypto.date,crypto.priceUsd,
                        crypto.priceEur,crypto.pricePln,crypto.priceBtc)
                db.cryptocurrencyOwnedDao().insertNewOwnedCryptocurrency(newOwnedCrypto)
            }
        }
        val oldDatabaseFile=getApplication<Application>().getDatabasePath("Cryptocurrency.db")
        if(oldDatabaseFile.exists()){
            oldDatabaseFile.delete()
            Log.d(TAG,"Old Database deleted Exist: ${oldDatabaseFile.exists()}")
        }
    }

    fun forMigrationTesting(){ //TODO: DELETE!!!!
        val oldDatabase=DatabaseController(getApplication<Application>().applicationContext)
        oldDatabase.addCrypto("BTC",0.01,"19-02-2019",1000.0,900.0,40000.0,1.0)
        oldDatabase.addCrypto("LSK",10.2,"19-02-2019",1000.0,900.0,40000.0,1.0)
        val oldDatabaseFile=getApplication<Application>().getDatabasePath("Cryptocurrency.db")
        Log.d(TAG,"Database: ${oldDatabaseFile.exists()}")
    }

    fun addNewCrypto(name:String, amount:Double){
        GlobalScope.launch{
            val cryptoDataInfo=db.cryptoDataListDao().getCryptoFromSymbol(name)
            if(cryptoDataInfo!=null){ //!!!
               RetrofitInstance.requestInterface.getCryptoPrices(cryptoDataInfo.id).observeOn(AndroidSchedulers.mainThread())
                       .subscribeOn(Schedulers.io())
                       .subscribe({cryptoInfo->
                           val currentDate=SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format (Date())
                           val decimalFormat=DecimalFormat("0.00")
                           val newCrypto=CryptocurrencyOwnedEntity(0,cryptoDataInfo.id,cryptoDataInfo.name,cryptoDataInfo.symbol,amount,
                                   currentDate,decimalFormat.format(cryptoInfo.quotes.USD.price.toDouble()).toDouble(),
                                   decimalFormat.format(cryptoInfo.quotes.EUR.price.toDouble()).toDouble(),
                                   decimalFormat.format(cryptoInfo.quotes.PLN.price.toDouble()).toDouble(),
                                   cryptoInfo.quotes.BTC.price.toDouble()
                           )
                           Log.d(TAG, "New crypto to add: $newCrypto")
                           GlobalScope.launch{
                               db.cryptocurrencyOwnedDao().insertNewOwnedCryptocurrency(newCrypto)
                               //TODO: Display new crypto!
                           }
                       },{ error ->
                           Log.d(TAG, "Error: $error")
                       })
            }else{
                Log.d(TAG, "Uncorrect name crypto")
            }
        }

    }
}
