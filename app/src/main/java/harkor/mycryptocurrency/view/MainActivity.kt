package harkor.mycryptocurrency.view


import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import harkor.mycryptocurrency.Cryptocurrency
import harkor.mycryptocurrency.Details
import harkor.mycryptocurrency.DetailsAdapter
import harkor.mycryptocurrency.R
import harkor.mycryptocurrency.viewmodels.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity /*implements ListRefresh,OverallPrice,InterfaceOfMainActivity*/() {
    //private val mAdView: AdView? = null
    private var mainActivityViewModel: MainActivityViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        mainActivityViewModel!!.getAmount()
                .observe(this, Observer { s -> text_money_amount!!.text = s })


        MobileAds.initialize(this, resources.getString( R.string.banner_ad_unit_id))
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        mainActivityViewModel!!.isDataDownloaded().observe(this,Observer{ dataFlag->
            if(!dataFlag){
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                progress_bar.visibility= View.VISIBLE
            }else{
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                progress_bar.visibility=View.INVISIBLE
            }
        })

        /////////////////////////////////////////////////////////////////////////////
        val recyclerView = list
        recyclerView.layoutManager = LinearLayoutManager(this)
        val cryptocurrencys = ArrayList<Cryptocurrency>()
        val details = ArrayList<Details>()
        details.add(Details("Bitcoin", "22-22-2222"))
        cryptocurrencys.add(Cryptocurrency("BTC", 0.00000001, details))
        cryptocurrencys.add(Cryptocurrency("BTC", 0.00000001, details))
        cryptocurrencys.add(Cryptocurrency("BTC", 0.00000001, details))
        val adapter = DetailsAdapter(cryptocurrencys)
        recyclerView.adapter = adapter
        /*////////////////////////////////////////////////////////////////////////////
        val db= Room.databaseBuilder(
                applicationContext, AppDatabase::class.java,"database-name"
        ).build()
        */////////////////////////////////////////////////////////////////////////////
        //val dbFile=applicationContext.getDatabasePath("Cryptocurrency.db")

        for(i in 1..2){
            Log.d("My crypto", i.toString())
            mainActivityViewModel!!.getAllPrice()
        }


    }
    /*
    @Override
    protected void onResume() {
        super.onResume();
        loadListView();
    }

    public void loadListView(){
        final ListDataEditor listDataEditor=new ListDataEditor(new DatabaseController(getApplicationContext()));

        ListViewAdapter listViewAdapter=new ListViewAdapter(this,listDataEditor.getNames(),listDataEditor.getAmounts());
        listView.setAdapter(listViewAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle=new Bundle();
                bundle.putInt("idCrypto",listDataEditor.dbId(i));
                DialogAdvanced dialogAdvanced=new DialogAdvanced();
                dialogAdvanced.setArguments(bundle);
                dialogAdvanced.show(getFragmentManager(), "advanced");
            }
        });
        onClickRefresh();
    }

    @Override
    public void refresh() {
        loadListView();
    }

    @Override
    public void setOverallPrice(String price) {
        moneyAmountText.setText(price);
    }

    @Override
    public int getCurrencyId() {
        SharedPreferences sharedPref =getApplicationContext().getSharedPreferences("harkor.myCryptocurrency", Context.MODE_PRIVATE);
        return sharedPref.getInt("currencyCode",1);

    }

    @Override
    public FragmentManager fragmentMenagerGetter() {
        return getFragmentManager();
    }

    @Override
    public DatabaseController databaseGetter() {
        return new DatabaseController(getApplicationContext());
    }
*/
}




/*
private class ExperimentalAsyncTask : AsyncTask<AppDatabase, Void, String>() {
    override fun doInBackground(vararg db: AppDatabase?): String {
        db.
    }

}*/