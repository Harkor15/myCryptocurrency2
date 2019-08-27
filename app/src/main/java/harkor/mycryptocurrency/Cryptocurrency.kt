package harkor.mycryptocurrency


import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup

class Cryptocurrency(title: String, amount: Double?, items: List<Details>) : ExpandableGroup<Details>(title, items)
