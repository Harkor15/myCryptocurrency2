package harkor.mycryptocurrency;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.LinkedList;

public class ListViewAdapter extends BaseAdapter{
    Context context;
    LinkedList<String> names;
    LinkedList<Double> amounts;

    public ListViewAdapter(Context context,LinkedList<String> names,LinkedList<Double> amounts) {
        this.context = context;
        this.amounts=(LinkedList<Double>)amounts.clone();
        this.names=(LinkedList<String>)names.clone();

    }

    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        view=inflater.inflate(R.layout.simple_list_item,null);

        TextView name=view.findViewById(R.id.text_name);
        TextView amount=view.findViewById(R.id.text_amount);

        name.setText(names.get(i));
        amount.setText(""+amounts.get(i));
        Log.d("MyCrypto",names.get(1));
        return view;
    }
}
