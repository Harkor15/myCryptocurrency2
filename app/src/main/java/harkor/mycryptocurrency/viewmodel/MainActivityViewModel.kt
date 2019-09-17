package harkor.mycryptocurrency.viewmodel

import android.app.Application
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import harkor.mycryptocurrency.*
import harkor.mycryptocurrency.model.ActualPrices
import harkor.mycryptocurrency.model.CryptoFullInfo
import harkor.mycryptocurrency.model.Cryptocurrency
import harkor.mycryptocurrency.model.CurrencyCalc
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
import kotlin.collections.ArrayList


class MainActivityViewModel(application: Application) : AndroidViewModel(application) {


    private val TAG = "MyCrypto"
    private var cryptoFullInfoList = ArrayList<CryptoFullInfo>()
    private val cryptocurrencys = ArrayList<Cryptocurrency>()
    private lateinit var amount: MutableLiveData<String>
    private lateinit var cryptoData: MutableLiveData<ArrayList<CryptoFullInfo>>
    var dataFlag = MutableLiveData<Boolean>()
    private var compositeDisposable: CompositeDisposable? = null
    val db = Room.databaseBuilder(
            getApplication<Application>().applicationContext,
            AppDataListDatabase::class.java, "CryptoDataClassEntity.db"
    ).build()


    fun getAmount(): LiveData<String> {
        amount = MutableLiveData()
        amount.value = "$1000000"
        return amount
    }

    fun getCryptoData(): MutableLiveData<ArrayList<CryptoFullInfo>> {
        cryptoData = MutableLiveData()
        cryptoData.value = ArrayList()
        return cryptoData
    }

    fun adapterDataForTest() { //TODO: Delete
        val details = ArrayList<Details>()
        details.add(Details("Bitcoin", "22-22-2222"))
        cryptocurrencys.add(Cryptocurrency("BTC", 0.00000001, details))
        //cryptoData.value = (cryptocurrencys)
    }

    fun isDataDownloaded(): MutableLiveData<Boolean> {
        val dbFile = getApplication<Application>().getDatabasePath("CryptoDataClassEntity.db")
        dataFlag.value = dbFile.exists()
        if (dataFlag.value == false) {
            getDataListFromApi()
        }
        return dataFlag
    }

    private fun getDataListFromApi() {
        Log.d(TAG, "add observer")
        compositeDisposable = CompositeDisposable()
        compositeDisposable?.add(RetrofitInstance.requestInterface.getListData()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError)
        )
    }

    private fun handleResponse(dataList: List<CryptoDataClassEntity>) {
        GlobalScope.launch {
            db.cryptoDataListDao().insertDataList(dataList)
            val oldDatabaseFile = getApplication<Application>().getDatabasePath("Cryptocurrency.db")
            if (oldDatabaseFile.exists()) {
                Log.d(TAG, "Migration needed!")
                migrateOldDatabase()
            } else {
                Log.d(TAG, "Migranion not needed!")
            }
            withContext(Dispatchers.Main) { dataFlag.value = true }
        }
    }

    private fun handleError(error: Throwable) {
        Log.d("MyCrypto", "Error: ${error.message} , ${error.cause}")
        Toast.makeText(getApplication<Application>().applicationContext, "Check your internet connection", Toast.LENGTH_SHORT).show() //TODO: Make string resource
        Handler().postDelayed({
            Log.d(TAG, "Try again!")
            getDataListFromApi()
        }, 5000)
    }

    fun getAllPrice() {
        GlobalScope.launch {
            var ownedCrypto = db.cryptocurrencyOwnedDao().getOwnedCryptocurrencys()
            Log.d(TAG, "Crypto from database size: ${ownedCrypto.size}")
            var cryptoNames = ""
            for (crypto in ownedCrypto) {
                cryptoNames += crypto.idtag
                cryptoNames += ","
                cryptoFullInfoList.add(CryptoFullInfo(crypto, ActualPrices()))
            }
            getPricesFromApi(cryptoNames)
        }
    }

    private fun getPricesFromApi(cryptoNames: String) {

        RetrofitInstance.requestInterface.getCryptoPrices(cryptoNames, "BTC,USD,EUR,PLN")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ priceData: Map<String, Map<String, Double>> ->
                    //Log.d(TAG, "Data response: $priceData")
                    for (i in 0 until cryptoFullInfoList.size) {
                        val priceInfo = priceData[cryptoFullInfoList[i].cryptoDbInfo.idtag]
                        if (priceInfo != null) {
                            cryptoFullInfoList[i].actualPrices.usd = priceInfo.getValue("usd")
                            cryptoFullInfoList[i].actualPrices.eur = priceInfo.getValue("eur")
                            cryptoFullInfoList[i].actualPrices.pln = priceInfo.getValue("pln")
                            cryptoFullInfoList[i].actualPrices.btc = priceInfo.getValue("btc")
                        }
                    }
                    //Log.d(TAG, "All data ready: /n $cryptoFullInfoList")
                    cryptoData.value=cryptoFullInfoList
                    amount.value="$1010101"
                    amount.value="$1010101"
                }, { error: Throwable ->
                    Log.d(TAG, "ERROR Retrofit: $error")
                    Toast.makeText(getApplication<Application>().applicationContext, R.string.check_internet_connection, Toast.LENGTH_SHORT).show()
                    Handler().postDelayed({
                        getPricesFromApi(cryptoNames)
                    }, 3000)
                })
    }

    /*
        private fun allPricecsHandleSucces( priceData:Map<String,Map<String,Double>>){
            Log.d(TAG,"Succes response amount: ${priceData[""]}")
            Log.d(TAG,"First record: $priceData")


        }
        private fun allPricesHandleError(error: Throwable){
            Log.d(TAG,"ERROR Retrofit: $error")
        }
    */
    private fun migrateOldDatabase() {
        val oldDatabase = DatabaseController(getApplication<Application>().applicationContext)
        val allData = oldDatabase.fullTable().toList()
        for (crypto in allData) {
            val dataListInfo = db.cryptoDataListDao().getCryptoFromSymbol(crypto.tag)
            if (dataListInfo != null) {//!!!
                val newOwnedCrypto = CryptocurrencyOwnedEntity(0, dataListInfo.id, dataListInfo.name,
                        dataListInfo.symbol, crypto.amount, crypto.date, crypto.priceUsd,
                        crypto.priceEur, crypto.pricePln, crypto.priceBtc)
                db.cryptocurrencyOwnedDao().insertNewOwnedCryptocurrency(newOwnedCrypto)
            }
        }
        val oldDatabaseFile = getApplication<Application>().getDatabasePath("Cryptocurrency.db")
        if (oldDatabaseFile.exists()) {
            oldDatabaseFile.delete()
            Log.d(TAG, "Old Database deleted Exist: ${oldDatabaseFile.exists()}")
        }
    }

    fun forMigrationTesting() { //TODO: DELETE!!!!
        val oldDatabase = DatabaseController(getApplication<Application>().applicationContext)
        oldDatabase.addCrypto("BTC", 0.01, "19-02-2019", 1000.0, 900.0, 40000.0, 1.0)
        oldDatabase.addCrypto("LSK", 10.2, "19-02-2019", 1000.0, 900.0, 40000.0, 1.0)
        val oldDatabaseFile = getApplication<Application>().getDatabasePath("Cryptocurrency.db")
        Log.d(TAG, "Database: ${oldDatabaseFile.exists()}")
    }

    fun addNewCrypto(name: String, amount: Double) {
        GlobalScope.launch {
            val cryptoDataInfo = db.cryptoDataListDao().getCryptoFromSymbol(name)
            if (cryptoDataInfo != null) { //!!!
                RetrofitInstance.requestInterface.getCryptoPrices(cryptoDataInfo.id, "BTC,USD,EUR,PLN").observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({ cryptoInfo ->
                            val currentDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
                            val newCrypto = CryptocurrencyOwnedEntity(0, cryptoDataInfo.id,
                                    cryptoDataInfo.name, cryptoDataInfo.symbol, amount, currentDate,
                                    cryptoInfo.getValue(cryptoDataInfo.id).getValue("usd"),
                                    cryptoInfo.getValue(cryptoDataInfo.id).getValue("eur"),
                                    cryptoInfo.getValue(cryptoDataInfo.id).getValue("pln"),
                                    cryptoInfo.getValue(cryptoDataInfo.id).getValue("btc")
                            )
                            Log.d(TAG, "New crypto to add: $newCrypto")
                            GlobalScope.launch {
                                val a=db.cryptocurrencyOwnedDao().insertNewOwnedCryptocurrency(newCrypto)
                                val newCrypto2=CryptocurrencyOwnedEntity(a.toInt(),newCrypto.idtag,
                                        newCrypto.name,newCrypto.symbol,newCrypto.amount,newCrypto.date,
                                        newCrypto.priceusd,newCrypto.priceeur,newCrypto.pricepln,newCrypto.pricebtc)

                                val addedCrypto= CryptoFullInfo(newCrypto2, ActualPrices(newCrypto.priceusd,
                                        newCrypto.priceeur, newCrypto.pricepln, newCrypto.pricebtc))
                                withContext(Dispatchers.Main){
                                    cryptoFullInfoList.add(addedCrypto)
                                    cryptoData.value=cryptoFullInfoList
                                }
                            }
                        }, { error ->
                            Log.d(TAG, "Error: $error")
                        })
            } else {
                Log.d(TAG, "Incorrect name crypto")
            }
        }
    }
    fun calculateAmount(){
        Log.d(TAG, "Full list size: ${cryptoFullInfoList.size}")
        val defaultCurrency=SharedPref.getDefaultCurrency(getApplication<Application>().applicationContext)
        var summaryPrice=0.0
        for (item in cryptoFullInfoList){
            when(defaultCurrency){
                1-> summaryPrice+=item.actualPrices.usd*item.cryptoDbInfo.amount
                2-> summaryPrice+=item.actualPrices.eur*item.cryptoDbInfo.amount
                3-> summaryPrice+=item.actualPrices.pln*item.cryptoDbInfo.amount
                else-> summaryPrice+= item.actualPrices.btc*item.cryptoDbInfo.amount
            }
        }
        val doubleFormat = DecimalFormat("0.00")
        amount.value= CurrencyCalc.addSign(doubleFormat.format(summaryPrice),defaultCurrency)
    }

}

