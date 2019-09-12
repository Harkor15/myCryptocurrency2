package harkor.mycryptocurrency


import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class EnityReadWriteTest {
    private lateinit var cryptoDataListDao: CryptoDataListDao
    private lateinit var cryptocurrencyOwnedDao: CryptocurrencyOwnedDao
    private lateinit var db: AppDataListDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().context
        db = Room.inMemoryDatabaseBuilder(
                context, AppDataListDatabase::class.java).build()
        cryptoDataListDao = db.cryptoDataListDao()
        cryptocurrencyOwnedDao = db.cryptocurrencyOwnedDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeAndReadInDataList() {
        val cryptoItemFromList = CryptoDataClassEntity("btc-bitcoin", "Bitcoin", "BTC")
        val list: List<CryptoDataClassEntity> = listOf(cryptoItemFromList)
        cryptoDataListDao.insertDataList(list)
        val cryptoItem = cryptoDataListDao.getCryptoFromSymbol(cryptoItemFromList.symbol)
        assertEquals(cryptoItemFromList, cryptoItem)
    }

    @Test
    @Throws(Exception::class)
    fun writeAndReadInCryptocurrencyOwned() {
        val newOwnedEntity = CryptocurrencyOwnedEntity(1, "btc-bitcoin", "Bitcoin",
                "BTC", 1.3, "10.09.2019",
                10000.0, 9000.0, 40000.0, 1.0)
        cryptocurrencyOwnedDao.insertNewOwnedCryptocurrency(newOwnedEntity)
        val newCryptoFromDatabase = cryptocurrencyOwnedDao.getOwnedCryptocurrencys()
        assertEquals(newOwnedEntity, newCryptoFromDatabase[0])
    }

}