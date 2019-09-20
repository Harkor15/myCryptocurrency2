package harkor.mycryptocurrency.services

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.*

class DatabaseController(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    /*
    fun addCrypto(tag: String, amount: Double, date: String, priceUsd: Double, priceEur: Double, pricePln: Double, priceBtc: Double) {
        val db = writableDatabase
        val values = ContentValues()
        values.put(FeedDatabase.COLUMN_NAME_TAG, tag)
        values.put(FeedDatabase.COLUMN_NAME_AMOUNT, amount)
        values.put(FeedDatabase.COLUMN_NAME_DATE, date)
        values.put(FeedDatabase.COLUMN_NAME_PRICE_USD, priceUsd)
        values.put(FeedDatabase.COLUMN_NAME_PRICE_EUR, priceEur)
        values.put(FeedDatabase.COLUMN_NAME_PRICE_PLN, pricePln)
        values.put(FeedDatabase.COLUMN_NAME_PRICE_BTC, priceBtc)
        db.insert(FeedDatabase.TABLE_NAME,
                null, values)
        db.close()
    }
    */


    fun fullTable(): LinkedList<Cryptocurrency> {
        val db = readableDatabase
        val sortOrder = FeedDatabase.COLUMN_NAME_ID + " ASC"
        val cursor = db.query(FeedDatabase.TABLE_NAME, null, null, null, null, null, sortOrder)
        val table = LinkedList<Cryptocurrency>()
        while (cursor.moveToNext()) {
            val crypto = Cryptocurrency(
                    cursor.getInt(cursor.getColumnIndexOrThrow(FeedDatabase.COLUMN_NAME_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(FeedDatabase.COLUMN_NAME_TAG)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(FeedDatabase.COLUMN_NAME_AMOUNT)),
                    cursor.getString(cursor.getColumnIndexOrThrow(FeedDatabase.COLUMN_NAME_DATE)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(FeedDatabase.COLUMN_NAME_PRICE_USD)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(FeedDatabase.COLUMN_NAME_PRICE_EUR)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(FeedDatabase.COLUMN_NAME_PRICE_PLN)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(FeedDatabase.COLUMN_NAME_PRICE_BTC))
            )
            table.add(crypto)
        }
        cursor.close()
        db.close()
        return table
    }

    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE)
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }

    companion object {
        val SQL_CREATE = "CREATE TABLE " + FeedDatabase.TABLE_NAME + " (" +
                FeedDatabase.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                FeedDatabase.COLUMN_NAME_TAG + " TEXT, " +
                FeedDatabase.COLUMN_NAME_AMOUNT + " DOUBLE, " +
                FeedDatabase.COLUMN_NAME_DATE + " DATE, " +
                FeedDatabase.COLUMN_NAME_PRICE_USD + " DOUBLE, " +
                FeedDatabase.COLUMN_NAME_PRICE_EUR + " DOUBLE, " +
                FeedDatabase.COLUMN_NAME_PRICE_PLN + " DOUBLE, " +
                FeedDatabase.COLUMN_NAME_PRICE_BTC + " DOUBLE)"
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "Cryptocurrency.db"
    }
}


object FeedDatabase {
    const val TABLE_NAME = "cryptocurrency"
    const val COLUMN_NAME_ID = "id"
    const val COLUMN_NAME_TAG = "tag"
    const val COLUMN_NAME_AMOUNT = "amount"
    const val COLUMN_NAME_DATE = "date"
    const val COLUMN_NAME_PRICE_USD = "priceusd"
    const val COLUMN_NAME_PRICE_EUR = "priceeur"
    const val COLUMN_NAME_PRICE_PLN = "pricepln"
    const val COLUMN_NAME_PRICE_BTC = "pricebtc"
}

data class Cryptocurrency(
        var id: Int,
        var tag: String,
        var amount: Double,
        var date: String,
        var priceUsd: Double,
        var priceEur: Double,
        var pricePln: Double,
        var priceBtc: Double
)