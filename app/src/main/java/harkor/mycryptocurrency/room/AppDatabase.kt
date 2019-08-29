package harkor.mycryptocurrency.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(CryptocurrencyData::class),version = 1 )
abstract class AppDatabase:RoomDatabase(){
    abstract fun cryptocurrencyDao():CryptocurrencyDao
}