package harkor.mycryptocurrency


import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry


import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

import org.junit.runner.RunWith
import java.io.IOException
import java.lang.Exception


@RunWith(AndroidJUnit4::class)
class EnityReadWriteTest{
    private lateinit var cryptoDataListDao: CryptoDataListDao
    private  lateinit var db: AppDataListDatabase

    @Before
    fun createDb(){
        val context = InstrumentationRegistry.getInstrumentation().context
        db= Room.inMemoryDatabaseBuilder(
                context, AppDataListDatabase::class.java).build()
        cryptoDataListDao=db.cryptoDataListDao()
    }
    @After
    @Throws(IOException::class)
    fun closeDb(){
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeAndReadInList(){
        val cryptoItemFromList=CryptoDataClassEntity("btc-bitcoin","Bitcoin","BTC")
        var list:List<CryptoDataClassEntity> =listOf(cryptoItemFromList)
        cryptoDataListDao.insertDataList(list)
        val cryptoItem=cryptoDataListDao.getCryptoFromSymbol(cryptoItemFromList.symbol)
        assertEquals(cryptoItemFromList,cryptoItem)
        assert(true)
    }

}