package harkor.mycryptocurrency.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import harkor.mycryptocurrency.OverallPrice;
import harkor.mycryptocurrency.model.ListRefresh;
import harkor.mycryptocurrency.services.DatabaseController;
import harkor.mycryptocurrency.ListViewAdapter;
import harkor.mycryptocurrency.R;
import harkor.mycryptocurrency.services.RetrofitInterface;
import harkor.mycryptocurrency.viewmodel.ListDataEditor;

public class MainActivity extends AppCompatActivity implements ListRefresh,OverallPrice{

    @BindView(R.id.text_money_amount) TextView moneyAmountText;
    @BindView(R.id.list)ListView listView;

    @OnClick(R.id.image_add)
    public void onClickAdd(){
        DialogAdd dialogAdd=new DialogAdd();
        dialogAdd.show(getFragmentManager(), "missiles");
    }
    @OnClick(R.id.image_refresh)
    public void onClickRefresh(){


    }
    @OnClick(R.id.image_settings)
    public void onClickSettings(){
        new DialogSettings().show(getFragmentManager(),"settings");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MyCryptocurrency","loadListView");
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
    }

    @Override
    public void refresh() {
        loadListView();
        Log.d("MyCryptocurrency","loadListView");
    }

    @Override
    public void setOverallPrice(String price) {
        moneyAmountText.setText(price);
    }
}
