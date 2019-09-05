package harkor.mycryptocurrency

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class DataRepository {

    val amount: MutableLiveData<String>
        get() {
            val amount = MutableLiveData<String>()
            amount.value = "$1000000"
            return amount
        }
    val retrofit =RetrofitClientInstance.instance.create(CoinpaprikaApi::class.java)

    companion object {
        private var instance: DataRepository? = null

        fun getInstance(): DataRepository {
            if (instance == null) {
                instance = DataRepository()
            }
            return instance as DataRepository
        }
    }
    fun deachData(){
        //retrofit.getCoinsDataList().observe(, Observer {  })
    }


}
