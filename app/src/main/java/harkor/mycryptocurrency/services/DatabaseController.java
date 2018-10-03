package harkor.mycryptocurrency.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.FractionRes;
import android.util.Log;
import android.widget.Toast;

import java.util.LinkedList;

import harkor.mycryptocurrency.R;
import harkor.mycryptocurrency.model.Cryptocurrency;
import harkor.mycryptocurrency.model.FeedDatabase;

public class DatabaseController extends SQLiteOpenHelper {
    Context context;
    public static final String SQL_CREATE="CREATE TABLE " + FeedDatabase.TABLE_NAME+" ("+
            FeedDatabase.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            FeedDatabase.COLUMN_NAME_TAG + " TEXT, " +
            FeedDatabase.COLUMN_NAME_AMOUNT + " DOUBLE, " +
            FeedDatabase.COLUMN_NAME_DATE + " DATE, " +
            FeedDatabase.COLUMN_NAME_PRICE_USD + " DOUBLE, " +
            FeedDatabase.COLUMN_NAME_PRICE_EUR + " DOUBLE, " +
            FeedDatabase.COLUMN_NAME_PRICE_PLN + " DOUBLE, " +
            FeedDatabase.COLUMN_NAME_PRICE_BTC + " DOUBLE)" ;
    private static final String SQL_DELETE ="DROP TABLE IF EXISTS " + FeedDatabase.TABLE_NAME;
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Cryptocurrency.db";


    public DatabaseController(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }
    public void addCrypto(String tag, double amount, String date, double priceUsd,double priceEur,double pricePln,double priceBtc){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(FeedDatabase.COLUMN_NAME_TAG,tag);
        values.put(FeedDatabase.COLUMN_NAME_AMOUNT,amount);
        values.put(FeedDatabase.COLUMN_NAME_DATE,date);
        values.put(FeedDatabase.COLUMN_NAME_PRICE_USD,priceUsd);
        values.put(FeedDatabase.COLUMN_NAME_PRICE_EUR,priceEur);
        values.put(FeedDatabase.COLUMN_NAME_PRICE_PLN,pricePln);
        values.put(FeedDatabase.COLUMN_NAME_PRICE_BTC,priceBtc);
        db.insert(FeedDatabase.TABLE_NAME,null,values);
        db.close();
        Log.d("MyCrypto", "DB add success!");
        Toast.makeText(context, R.string.db_addes,Toast.LENGTH_SHORT).show();

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
                    cursor.getDouble(cursor.getColumnIndexOrThrow(FeedDatabase.COLUMN_NAME_PRICE_PLN)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(FeedDatabase.COLUMN_NAME_PRICE_BTC))
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
    public Cryptocurrency singleCrypto(int id){
        SQLiteDatabase db = getReadableDatabase();
        String selection = FeedDatabase.COLUMN_NAME_ID + " = ?";
        String[] selectionArgs = { ""+id };

        Cursor cursor = db.query(
                FeedDatabase.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        cursor.moveToFirst();
        Cryptocurrency crypto=new Cryptocurrency(
                cursor.getInt(cursor.getColumnIndexOrThrow(FeedDatabase.COLUMN_NAME_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(FeedDatabase.COLUMN_NAME_TAG)),
                cursor.getDouble(cursor.getColumnIndexOrThrow(FeedDatabase.COLUMN_NAME_AMOUNT)),
                cursor.getString(cursor.getColumnIndexOrThrow(FeedDatabase.COLUMN_NAME_DATE)),
                cursor.getDouble(cursor.getColumnIndexOrThrow(FeedDatabase.COLUMN_NAME_PRICE_USD)),
                cursor.getDouble(cursor.getColumnIndexOrThrow(FeedDatabase.COLUMN_NAME_PRICE_EUR)),
                cursor.getDouble(cursor.getColumnIndexOrThrow(FeedDatabase.COLUMN_NAME_PRICE_PLN)),
                cursor.getDouble(cursor.getColumnIndexOrThrow(FeedDatabase.COLUMN_NAME_PRICE_BTC))
        );
        return crypto;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE);
        Log.d("MyCrypto","Database created!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
