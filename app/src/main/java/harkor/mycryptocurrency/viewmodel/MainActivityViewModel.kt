package harkor.mycryptocurrency.viewmodel

import android.app.Application
import android.os.Handler
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import harkor.mycryptocurrency.R
import harkor.mycryptocurrency.model.ActualPrices
import harkor.mycryptocurrency.model.CryptoFullInfo
import harkor.mycryptocurrency.services.*
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
    private var cryptoFullInfoList = ArrayList<CryptoFullInfo>()
    private lateinit var amount: MutableLiveData<String>
    private lateinit var cryptoData: MutableLiveData<ArrayList<CryptoFullInfo>>
    var dataFlag = MutableLiveData<Boolean>()
    private var compositeDisposable: CompositeDisposable? = null
    private val db = Room.databaseBuilder(
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

    fun isDataDownloaded(): MutableLiveData<Boolean> {
        val dbFile = getApplication<Application>().getDatabasePath("CryptoDataClassEntity.db")
        dataFlag.value = dbFile.exists()
        if (dataFlag.value == false) {
            getDataListFromApi()
        }
        return dataFlag
    }

    private fun getDataListFromApi() {
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
                migrateOldDatabase()
            }
            withContext(Dispatchers.Main) { dataFlag.value = true }
        }
    }

    private fun handleError(error: Throwable) {
        Toast.makeText(getApplication<Application>().applicationContext, "Check your internet connection", Toast.LENGTH_SHORT).show() //TODO: Make string resource
        Handler().postDelayed({
            getDataListFromApi()
        }, 5000)
    }

    fun getAllPrice() {
        GlobalScope.launch {
            val ownedCrypto = db.cryptocurrencyOwnedDao().getOwnedCryptocurrencys()
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
                    for (i in 0 until cryptoFullInfoList.size) {
                        val priceInfo = priceData[cryptoFullInfoList[i].cryptoDbInfo.idtag]
                        if (priceInfo != null) {
                            cryptoFullInfoList[i].actualPrices.usd = priceInfo.getValue("usd")
                            cryptoFullInfoList[i].actualPrices.eur = priceInfo.getValue("eur")
                            cryptoFullInfoList[i].actualPrices.pln = priceInfo.getValue("pln")
                            cryptoFullInfoList[i].actualPrices.btc = priceInfo.getValue("btc")
                        }
                    }
                    cryptoData.value = cryptoFullInfoList
                    amount.value = "$1010101"
                    amount.value = "$1010101"
                }, { error: Throwable ->
                    Toast.makeText(getApplication<Application>().applicationContext, R.string.check_internet_connection, Toast.LENGTH_SHORT).show()
                    Handler().postDelayed({
                        getPricesFromApi(cryptoNames)
                    }, 3000)
                })
    }

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
        }
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
                            GlobalScope.launch {
                                val a = db.cryptocurrencyOwnedDao().insertNewOwnedCryptocurrency(newCrypto)
                                val newCrypto2 = CryptocurrencyOwnedEntity(a.toInt(), newCrypto.idtag,
                                        newCrypto.name, newCrypto.symbol, newCrypto.amount, newCrypto.date,
                                        newCrypto.priceusd, newCrypto.priceeur, newCrypto.pricepln, newCrypto.pricebtc)

                                val addedCrypto = CryptoFullInfo(newCrypto2, ActualPrices(newCrypto.priceusd,
                                        newCrypto.priceeur, newCrypto.pricepln, newCrypto.pricebtc))
                                withContext(Dispatchers.Main) {
                                    cryptoFullInfoList.add(addedCrypto)
                                    cryptoData.value = cryptoFullInfoList
                                }
                            }
                        }, {
                            Toast.makeText(getApplication<Application>().applicationContext, R.string.check_internet_connection, Toast.LENGTH_SHORT).show()
                        })
            } else {
                withContext(Dispatchers.Main) {
                    Toast.makeText(getApplication<Application>().applicationContext, R.string.invalid_cryptocurrency_name, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun calculateAmount() {
        val defaultCurrency = SharedPref.getDefaultCurrency(getApplication<Application>().applicationContext)
        var summaryPrice = 0.0
        for (item in cryptoFullInfoList) {
            when (defaultCurrency) {
                1 -> summaryPrice += item.actualPrices.usd * item.cryptoDbInfo.amount
                2 -> summaryPrice += item.actualPrices.eur * item.cryptoDbInfo.amount
                3 -> summaryPrice += item.actualPrices.pln * item.cryptoDbInfo.amount
                else -> summaryPrice += item.actualPrices.btc * item.cryptoDbInfo.amount
            }
        }
        val doubleFormat = DecimalFormat("0.00")
        amount.value = CurrencyCalc.addSign(doubleFormat.format(summaryPrice), defaultCurrency)
    }
}

