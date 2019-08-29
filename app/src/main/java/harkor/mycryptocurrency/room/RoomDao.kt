package harkor.mycryptocurrency.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query


@Dao
interface CryptocurrencyDao{
    @Query("SELECT * FROM cryptocurrency")
    fun getAll(): LiveData<List<CryptocurrencyData>>
}