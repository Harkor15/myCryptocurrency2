package harkor.mycryptocurrency.view;

import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import harkor.mycryptocurrency.Interfaces.InterfaceOfMainActivity;
import harkor.mycryptocurrency.Interfaces.ListRefresh;
import harkor.mycryptocurrency.Interfaces.OverallPrice;
import harkor.mycryptocurrency.services.DatabaseController;
import harkor.mycryptocurrency.services.ListViewAdapter;
import harkor.mycryptocurrency.R;
import harkor.mycryptocurrency.services.ListDataEditor;
import harkor.mycryptocurrency.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity implements ListRefresh,OverallPrice,InterfaceOfMainActivity {
    private AdView mAdView;
    MainViewModel mainViewModel;
    @BindView(R.id.text_money_amount) TextView moneyAmountText;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        MobileAds.initialize(this, String.valueOf(R.string.banner_ad_unit_id));
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);



        mainViewModel=new MainViewModel(this);
    }

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

}
