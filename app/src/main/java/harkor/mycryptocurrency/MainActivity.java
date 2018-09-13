package harkor.mycryptocurrency;

import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    SimpleCursorAdapter mAdapter;

    // These are the Contacts rows that we will retrieve
    static final String[] PROJECTION = new String[] {"BTC","0,01"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView=getListView();
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
