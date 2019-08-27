package harkor.mycryptocurrency.view


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdView
import harkor.mycryptocurrency.Cryptocurrency
import harkor.mycryptocurrency.Details
import harkor.mycryptocurrency.DetailsAdapter
import harkor.mycryptocurrency.R
import harkor.mycryptocurrency.viewmodels.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity /*implements ListRefresh,OverallPrice,InterfaceOfMainActivity*/() {
    private val mAdView: AdView? = null
    private var mainActivityViewModel: MainActivityViewModel? = null

    //internal var moneyAmountText: TextView? = null


    /*
    MainViewModel mainViewModel;

    @BindView(R.id.list)ListView listView;

    @OnClick(R.id.image_add)
    public void onClickAdd(){
        mainViewModel.addCrypto();
    }
    @OnClick(R.id.image_refresh)
    public void onClickRefresh(){
        mainViewModel.refresh(this);
    }
    @OnClick(R.id.image_settings)
    public void onClickSettings(){
        mainViewModel.settings();

    }
*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //ButterKnife.bind(this)
        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        mainActivityViewModel!!.getAmount()
                .observe(this, Observer { s -> text_money_amount!!.text = s })
        /*

        MobileAds.initialize(this, String.valueOf(R.string.banner_ad_unit_id));
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mainViewModel=new MainViewModel(this);
        */
        val recyclerView = findViewById<RecyclerView>(R.id.list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val cryptocurrencys = ArrayList<Cryptocurrency>()
        val details = ArrayList<Details>()
        details.add(Details("Bitcoin", "22-22-2222"))
        cryptocurrencys.add(Cryptocurrency("BTC", 0.00000001, details))
        cryptocurrencys.add(Cryptocurrency("BTC", 0.00000001, details))
        cryptocurrencys.add(Cryptocurrency("BTC", 0.00000001, details))

        val adapter = DetailsAdapter(cryptocurrencys)
        recyclerView.adapter = adapter


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
