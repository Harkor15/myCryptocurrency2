package harkor.mycryptocurrency;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
