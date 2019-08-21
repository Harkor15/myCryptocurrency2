package harkor.mycryptocurrency;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class DetailsAdapter extends ExpandableRecyclerViewAdapter<CryptoViewHolder, DetailsViewHolder> {
    public DetailsAdapter(List<? extends ExpandableGroup> groups) {
        super(groups);
    }

    @Override
    public CryptoViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.expandable_recyclerview_crypto,parent,false);
        return new CryptoViewHolder(v);
    }

    @Override
    public DetailsViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.expandable_recyclerview_details,parent,false);
        return new DetailsViewHolder(v);
    }

    @Override
    public void onBindChildViewHolder(DetailsViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        final Details details= (Details) group.getItems().get(childIndex);
        holder.bind(details);
    }

    @Override
    public void onBindGroupViewHolder(CryptoViewHolder holder, int flatPosition, ExpandableGroup group) {
        final Cryptocurrency cryptocurrency=(Cryptocurrency) group;
        holder.bind(cryptocurrency);
    }
}
