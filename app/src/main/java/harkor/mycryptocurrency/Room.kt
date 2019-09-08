package harkor.mycryptocurrency

import androidx.room.*

//class CryptoDataListItem(val id:String,val name:String,val symbol: String)

@Entity
data class CryptoDataClassEntity(
        @PrimaryKey var id:String,
        @ColumnInfo(name="name") var name:String,
        @ColumnInfo(name="symbol") var symbol:String
)

@Dao
interface CryptoDataListDao{
    @Query("SELECT * FROM cryptodataclassentity WHERE symbol LIKE:symbol")
    fun getCryptoFromSymbol(symbol:String):CryptoDataClassEntity

    @Insert
    fun insertDataListItem(vararg cryptoData:CryptoDataClassEntity)

    //@Query("DROP TABLE cryptodataclassentity")
    //fun deleteAll()
}

@Database( entities= [CryptoDataClassEntity::class],version=1)
abstract class AppDataListDatabase: RoomDatabase(){
    abstract fun cryptoDataListDao():CryptoDataListDao
}