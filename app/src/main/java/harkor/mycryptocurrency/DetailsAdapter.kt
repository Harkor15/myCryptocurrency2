package harkor.mycryptocurrency

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup

class DetailsAdapter(groups: List<ExpandableGroup<*>>) : ExpandableRecyclerViewAdapter<CryptoViewHolder, DetailsViewHolder>(groups) {

    override fun onCreateGroupViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.expandable_recyclerview_crypto, parent, false)
        return CryptoViewHolder(v)
    }

    override fun onCreateChildViewHolder(parent: ViewGroup, viewType: Int): DetailsViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.expandable_recyclerview_details, parent, false)
        return DetailsViewHolder(v)
    }

    override fun onBindChildViewHolder(holder: DetailsViewHolder, flatPosition: Int, group: ExpandableGroup<*>, childIndex: Int) {
        val details = group.items[childIndex] as Details
        holder.bind(details)
    }

    override fun onBindGroupViewHolder(holder: CryptoViewHolder, flatPosition: Int, group: ExpandableGroup<*>) {
        val cryptocurrency = group as Cryptocurrency
        holder.bind(cryptocurrency)
    }

    public fun setCryptocurrencys(){

    }
}
