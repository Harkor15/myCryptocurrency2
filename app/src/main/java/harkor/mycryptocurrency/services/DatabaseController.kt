package harkor.mycryptocurrency.services

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import harkor.mycryptocurrency.R
import harkor.mycryptocurrency.model.Cryptocurrency
import harkor.mycryptocurrency.model.FeedDatabase
import java.util.*

class DatabaseController(internal var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

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
        Log.d("MyCrypto", "DB add success!")
        Toast.makeText(context, R.string.db_addes, Toast.LENGTH_SHORT).show()

    }

    fun deleteCrypto(id: Int) {
        val db = writableDatabase
        val selection = FeedDatabase.COLUMN_NAME_ID + " LIKE ?"
        val selectionArgs = arrayOf(id.toString() + "")
        db.delete(FeedDatabase.TABLE_NAME, selection, selectionArgs)
        db.close()
    }

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

    fun smallTable(): LinkedList<Cryptocurrency> {
        val db = readableDatabase
        val sortOrder = FeedDatabase.COLUMN_NAME_ID + " ASC"
        val cursor = db.query(FeedDatabase.TABLE_NAME, null, null, null, null, null, sortOrder)
        val table = LinkedList<Cryptocurrency>()
        while (cursor.moveToNext()) {
            val crypto = Cryptocurrency(
                    cursor.getInt(cursor.getColumnIndexOrThrow(FeedDatabase.COLUMN_NAME_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(FeedDatabase.COLUMN_NAME_TAG)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(FeedDatabase.COLUMN_NAME_AMOUNT))
            )
            table.add(crypto)
        }
        cursor.close()
        db.close()
        return table
    }

    fun singleCrypto(id: Int): Cryptocurrency {
        val db = readableDatabase
        val selection = FeedDatabase.COLUMN_NAME_ID + " = ?"
        val selectionArgs = arrayOf("" + id)

        val cursor = db.query(
                FeedDatabase.TABLE_NAME, null,
                selection,
                selectionArgs, null, null, null
        )
        cursor.moveToFirst()
        return Cryptocurrency(
                cursor.getInt(cursor.getColumnIndexOrThrow(FeedDatabase.COLUMN_NAME_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(FeedDatabase.COLUMN_NAME_TAG)),
                cursor.getDouble(cursor.getColumnIndexOrThrow(FeedDatabase.COLUMN_NAME_AMOUNT)),
                cursor.getString(cursor.getColumnIndexOrThrow(FeedDatabase.COLUMN_NAME_DATE)),
                cursor.getDouble(cursor.getColumnIndexOrThrow(FeedDatabase.COLUMN_NAME_PRICE_USD)),
                cursor.getDouble(cursor.getColumnIndexOrThrow(FeedDatabase.COLUMN_NAME_PRICE_EUR)),
                cursor.getDouble(cursor.getColumnIndexOrThrow(FeedDatabase.COLUMN_NAME_PRICE_PLN)),
                cursor.getDouble(cursor.getColumnIndexOrThrow(FeedDatabase.COLUMN_NAME_PRICE_BTC))
        )
    }

    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE)
        Log.d("MyCrypto", "Database created!")
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        Log.d(Companion.TAG,"Migration from $oldVersion to $newVersion")
        if(oldVersion==1){

        }

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
        private val SQL_DELETE = "DROP TABLE IF EXISTS " + FeedDatabase.TABLE_NAME
        val DATABASE_VERSION = 2
        val DATABASE_NAME = "Cryptocurrency.db"
        private const val TAG="MyCrypto"
    }
}
