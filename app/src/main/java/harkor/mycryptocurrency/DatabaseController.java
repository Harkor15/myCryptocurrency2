package harkor.mycryptocurrency;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQuery;
import android.util.Log;

import java.util.LinkedList;

import harkor.mycryptocurrency.model.Cryptocurrency;

public class DatabaseController extends SQLiteOpenHelper {

    public static final String SQL_CREATE="CREATE TABLE " + FeedDatabase.TABLE_NAME+" ("+
            FeedDatabase.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            FeedDatabase.COLUMN_NAME_TAG + " TEXT, " +
            FeedDatabase.COLUMN_NAME_AMOUNT + "DOUBLE, " +
            FeedDatabase.COLUMN_NAME_DATE + " DATE, " +
            FeedDatabase.COLUMN_NAME_PRICE_USD + " DOUBLE, " +
            FeedDatabase.COLUMN_NAME_PRICE_EUR + " DOUBLE, " +
            FeedDatabase.COLUMN_NAME_PRICE_PLN + " DOUBLE )";
    private static final String SQL_DELETE ="DROP TABLE IF EXISTS " + FeedDatabase.TABLE_NAME;
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Cryptocurrency.db";


    public DatabaseController(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void addCrypto(String tag, String amount, String date, String priceUsd,String priceEur,String pricePln){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(FeedDatabase.COLUMN_NAME_TAG,tag);
        values.put(FeedDatabase.COLUMN_NAME_AMOUNT,amount);
        values.put(FeedDatabase.COLUMN_NAME_DATE,date);
        values.put(FeedDatabase.COLUMN_NAME_PRICE_USD,priceUsd);
        values.put(FeedDatabase.COLUMN_NAME_PRICE_EUR,priceEur);
        values.put(FeedDatabase.COLUMN_NAME_PRICE_PLN,pricePln);
        db.insert(FeedDatabase.TABLE_NAME,null,values);
        db.close();
    }
    public void deleteCrypto(int id){
        SQLiteDatabase db=getWritableDatabase();
        String selection = FeedDatabase.COLUMN_NAME_ID + " LIKE ?";
        String[] selectionArgs = { id+"" };
        db.delete(FeedDatabase.TABLE_NAME, selection, selectionArgs);
        db.close();
    }
    public void edicCrypto(int id,double newAmount){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FeedDatabase.COLUMN_NAME_AMOUNT, newAmount);
        String selection = FeedDatabase.COLUMN_NAME_ID + " LIKE ?";
        String[] selectionArgs = { ""+id };
        db.update(
                FeedDatabase.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        db.close();
    }
    public LinkedList<Cryptocurrency> fullTable(){
        SQLiteDatabase db = getReadableDatabase();
        String sortOrder= FeedDatabase.COLUMN_NAME_ID+" ASC";
        Cursor cursor=db.query(FeedDatabase.TABLE_NAME,null,null,
                null,null,null,sortOrder);
        LinkedList<Cryptocurrency> table=new LinkedList<>();
        while(cursor.moveToNext()){
            Cryptocurrency crypto=new Cryptocurrency(
                    cursor.getInt(cursor.getColumnIndexOrThrow(FeedDatabase.COLUMN_NAME_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(FeedDatabase.COLUMN_NAME_TAG)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(FeedDatabase.COLUMN_NAME_AMOUNT)),
                    cursor.getString(cursor.getColumnIndexOrThrow(FeedDatabase.COLUMN_NAME_DATE)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(FeedDatabase.COLUMN_NAME_PRICE_USD)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(FeedDatabase.COLUMN_NAME_PRICE_EUR)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(FeedDatabase.COLUMN_NAME_PRICE_PLN))
            );
            table.add(crypto);
        }
        cursor.close();
        db.close();
        return table;
    }
    public LinkedList<Cryptocurrency> smallTable() {
        SQLiteDatabase db = getReadableDatabase();
        String sortOrder = FeedDatabase.COLUMN_NAME_ID + " ASC";
        Cursor cursor = db.query(FeedDatabase.TABLE_NAME, null, null,
                null, null, null, sortOrder);
        LinkedList<Cryptocurrency> table = new LinkedList<>();
        while (cursor.moveToNext()) {
            Cryptocurrency crypto = new Cryptocurrency(
                    cursor.getInt(cursor.getColumnIndexOrThrow(FeedDatabase.COLUMN_NAME_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(FeedDatabase.COLUMN_NAME_TAG)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(FeedDatabase.COLUMN_NAME_AMOUNT))
            );
            table.add(crypto);
        }
        cursor.close();
        db.close();
        return table;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL(SQL_CREATE);
        Log.d("MyCrypto","Database created!");
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
