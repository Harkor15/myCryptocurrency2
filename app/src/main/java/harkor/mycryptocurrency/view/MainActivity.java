package harkor.mycryptocurrency.view;

import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.LinkedList;

import harkor.mycryptocurrency.DatabaseController;
import harkor.mycryptocurrency.ListViewAdapter;
import harkor.mycryptocurrency.R;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ImageView imageAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=findViewById(R.id.list);
        imageAdd=findViewById(R.id.image_add);
        imageAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogAdd dialogAdd=new DialogAdd();
                dialogAdd.show(getFragmentManager(), "missiles");
            }
        });
        loadListView();
        DatabaseController databaseController=new DatabaseController(getApplicationContext());

    }
    public void loadListView(){
        LinkedList<String> n=new LinkedList<>();
        LinkedList<Double> a=new LinkedList<>();
        n.add("LSK");a.add(5.0);n.add("BTC");a.add(0.01);n.add("LSK");a.add(5.0);n.add("BTC");a.add(0.01);
        n.add("LSK");a.add(5.0);n.add("BTC");a.add(0.01);n.add("LSK");a.add(5.0);n.add("BTC");a.add(0.01);
        n.add("LSK");a.add(5.0);n.add("BTC");a.add(0.01);n.add("LSK");a.add(5.0);n.add("BTC");a.add(0.01);
        ListViewAdapter listViewAdapter=new ListViewAdapter(this,n,a);
        listView.setAdapter(listViewAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("MyCrypto","click "+i);
            }
        });
    }



}
