package harkor.mycryptocurrency

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder


class DetailsAdapter(groups: List<ExpandableGroup<*>>) : CustomExpandableRecyclerViewAdapter<CryptoViewHolder, DetailsViewHolder>(groups) {

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
        var cryptocurrency = group as Cryptocurrency
        holder.bind(cryptocurrency)
    }

    fun addAll(groups: MutableList<Details>) {
        //(getGroups() as ArrayList<*>).addAll(groups)

        (getGroups() as MutableList<*>)
        notifyGroupDataChanged()
        notifyDataSetChanged()
    }
}


abstract class CustomExpandableRecyclerViewAdapter<GVH : GroupViewHolder, CVH : ChildViewHolder>(groups: List<ExpandableGroup<*>>) : ExpandableRecyclerViewAdapter<GVH, CVH>(groups) {

    fun notifyGroupDataChanged() {
        expandableList.expandedGroupIndexes = BooleanArray(groups.size)
        for (i in 0 until groups.size) {
            expandableList.expandedGroupIndexes[i] = false
        }
    }
}