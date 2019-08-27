package harkor.mycryptocurrency

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class DataRepository {

    val amount: MutableLiveData<String>
        get() {
            val amount = MutableLiveData<String>()
            amount.value = "$1000000"
            return amount
        }

    companion object {
        private var instance: DataRepository? = null

        fun getInstance(): DataRepository {
            if (instance == null) {
                instance = DataRepository()
            }
            return instance as DataRepository
        }
    }


}
