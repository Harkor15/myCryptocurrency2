package harkor.mycryptocurrency;

import android.os.Parcel;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class Cryptocurrency extends ExpandableGroup<Details> {
    public Cryptocurrency(String title, Double amount, List<Details> items) {
        super(title, items);
    }
}
