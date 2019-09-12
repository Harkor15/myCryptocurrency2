package harkor.mycryptocurrency

import androidx.room.*

// CRYPTO DATA LIST

@Entity
data class CryptoDataClassEntity(
        @PrimaryKey var id: String,
        @ColumnInfo(name = "name") var name: String,
        @ColumnInfo(name = "symbol") var symbol: String
)

@Dao
interface CryptoDataListDao {
    @Query("SELECT * FROM cryptodataclassentity WHERE symbol LIKE:symbol")
    fun getCryptoFromSymbol(symbol: String): CryptoDataClassEntity

    @Insert
    fun insertDataList(cryptoData: List<CryptoDataClassEntity>)
}

// OWNED CRYPTO LIST

@Entity
data class CryptocurrencyOwnedEntity(
        @PrimaryKey(autoGenerate = true) val id: Int,
        val idtag: String,
        val name: String,
        val symbol: String,
        val amount: Double,
        val date: String,
        val priceusd: Double,
        val priceeur: Double,
        val pricepln: Double,
        val pricebtc: Double
)

@Dao
interface CryptocurrencyOwnedDao {
    @Query("SELECT * FROM cryptocurrencyownedentity")
    fun getOwnedCryptocurrencys(): List<CryptocurrencyOwnedEntity>

    @Insert
    fun insertNewOwnedCryptocurrency(newCrypto: CryptocurrencyOwnedEntity)
}


@Database(entities = [CryptoDataClassEntity::class, CryptocurrencyOwnedEntity::class], version = 1)
abstract class AppDataListDatabase : RoomDatabase() {
    abstract fun cryptoDataListDao(): CryptoDataListDao
    abstract fun cryptocurrencyOwnedDao(): CryptocurrencyOwnedDao
}