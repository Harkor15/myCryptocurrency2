package harkor.mycryptocurrency.view;

import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.LinkedList;

import harkor.mycryptocurrency.ListViewAdapter;
import harkor.mycryptocurrency.R;

public class MainActivity extends AppCompatActivity {
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=findViewById(R.id.list);
        loadListView();

    }
    public void loadListView(){
        LinkedList<String> n=new LinkedList<>();
        LinkedList<Double> a=new LinkedList<>();
        n.add("LSK");
        a.add(5.0);
        n.add("BTC");
        a.add(0.01);
        ListViewAdapter listViewAdapter=new ListViewAdapter(this,n,a);
        listView.setAdapter(listViewAdapter);
    }



    public int testOfTesting(int number){
        return number*10;
    }
    public boolean isMoreThanZero(int number){
        if(number>0){
            return true;
        }
        return false;
    }
}
