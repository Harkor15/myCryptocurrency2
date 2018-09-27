package harkor.mycryptocurrency.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.LinkedList;

import harkor.mycryptocurrency.CryptoAdd;
import harkor.mycryptocurrency.model.ListRefresh;
import harkor.mycryptocurrency.services.DatabaseController;
import harkor.mycryptocurrency.ListViewAdapter;
import harkor.mycryptocurrency.R;
import harkor.mycryptocurrency.services.RetrofitClientInstance;
import harkor.mycryptocurrency.services.RetrofitInterface;
import harkor.mycryptocurrency.viewmodel.ListDataEditor;

public class MainActivity extends AppCompatActivity implements ListRefresh{
    ListView listView;
    ImageView imageAdd, imageRefresh, imageSettings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=findViewById(R.id.list);
        imageAdd=findViewById(R.id.image_add);
        imageRefresh=findViewById(R.id.image_refresh);
        imageSettings=findViewById(R.id.image_settings);
        imageAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogAdd dialogAdd=new DialogAdd();
                dialogAdd.show(getFragmentManager(), "missiles");
            }
        });
        imageRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //RetrofitInterface retrofitInterface=new RetrofitInterface();
                //retrofitInterface.multiCrypto("BTC,LTC,ETH");

            }
        });
        imageSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DialogSettings().show(getFragmentManager(),"settings");
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        loadListView();
    }

    public void loadListView(){
        ListDataEditor listDataEditor=new ListDataEditor(new DatabaseController(getApplicationContext()));
        ListViewAdapter listViewAdapter=new ListViewAdapter(this,listDataEditor.getNames(),listDataEditor.getAmounts());
        listView.setAdapter(listViewAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("MyCrypto","click "+i);
            }
        });
    }

    @Override
    public void refresh() {
        loadListView();
    }
}
