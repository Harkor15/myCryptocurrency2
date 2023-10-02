package harkor.mycryptocurrency.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import harkor.mycryptocurrency.R
import harkor.mycryptocurrency.model.CryptoFullInfo
import harkor.mycryptocurrency.services.AppDataListDatabase
import harkor.mycryptocurrency.services.CurrencyCalc
import harkor.mycryptocurrency.services.SharedPref
import harkor.mycryptocurrency.view.NotifyDataDelete
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.w3c.dom.Text
import java.text.DecimalFormat

class RecyclerAdapter(var dataSet: ArrayList<CryptoFullInfo>, val notifyDataDelete: NotifyDataDelete) : RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder>() {

    class RecyclerViewHolder(var myView: View) : RecyclerView.ViewHolder(myView)

    private var greenColor: Int? = null
    private var redColor: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val myView = LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_view_item, parent, false)
        greenColor = ContextCompat.getColor(parent.context, R.color.light_green)
        redColor = ContextCompat.getColor(parent.context, R.color.light_red)
        return RecyclerViewHolder(myView)
    }

    override fun getItemCount() = dataSet.size

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val defaultCurrency = SharedPref.getDefaultCurrency(holder.myView.context)
        val nowPrice: Double
        val historicalPrice: Double
        when (defaultCurrency) {
            1 -> {
                nowPrice = dataSet[position].actualPrices.usd
                historicalPrice = dataSet[position].cryptoDbInfo.priceusd
            }
            2 -> {
                nowPrice = dataSet[position].actualPrices.eur
                historicalPrice = dataSet[position].cryptoDbInfo.priceeur
            }
            3 -> {
                nowPrice = dataSet[position].actualPrices.pln
                historicalPrice = dataSet[position].cryptoDbInfo.pricepln
            }
            else -> {
                nowPrice = dataSet[position].actualPrices.btc
                historicalPrice = dataSet[position].cryptoDbInfo.pricebtc
            }
        }
        val doubleFormat = DecimalFormat("0.00")
        val historicalPrices = "${CurrencyCalc.addSign(doubleFormat.format(historicalPrice * dataSet[position].cryptoDbInfo.amount)
                , defaultCurrency)} (${CurrencyCalc.addSign(doubleFormat.format(historicalPrice), defaultCurrency)})"
        holder.itemView.findViewById<TextView>(R.id.recycler_historical_prices).text = historicalPrices
        //holder.itemView.recycler_tag.text = dataSet[position].cryptoDbInfo.symbol.toUpperCase()
        holder.itemView.findViewById<TextView>(R.id.recycler_tag).text = dataSet[position].cryptoDbInfo.name
        holder.itemView.findViewById<TextView>(R.id.recycler_amount).text = dataSet[position].cryptoDbInfo.amount.toString()
        //holder.itemView.recycler_name.text = dataSet[position].cryptoDbInfo.name
        val actPrice = "${dataSet[position].cryptoDbInfo.symbol.uppercase()} - ${CurrencyCalc.addSign(doubleFormat.format(nowPrice), defaultCurrency)}"
        holder.itemView.findViewById<TextView>(R.id.recycler_actual_price).text = actPrice
        val actValue = nowPrice * dataSet[position].cryptoDbInfo.amount
        val percentageDiff = (nowPrice * 100) / historicalPrice - 100
        var percentageDiffStr = doubleFormat.format(percentageDiff) + "%"
        if (percentageDiff > 0) {
            percentageDiffStr =     "+$percentageDiffStr"
            holder.myView.findViewById<TextView>(R.id.recycler_balance_percentage).setTextColor(greenColor!!)
            holder.myView.findViewById<TextView>(R.id.recycler_balance_value).setTextColor(greenColor!!)
        } else {
            holder.myView.findViewById<TextView>(R.id.recycler_balance_percentage).setTextColor(redColor!!)
            holder.myView.findViewById<TextView>(R.id.recycler_balance_value).setTextColor(redColor!!)
        }
        holder.itemView.findViewById<TextView>(R.id.recycler_balance_percentage).text = percentageDiffStr
        val diff = actValue - historicalPrice * dataSet[position].cryptoDbInfo.amount
        val diffStr = "(${CurrencyCalc.addSign(doubleFormat.format(diff), defaultCurrency)})"
        holder.itemView.findViewById<TextView>(R.id.recycler_balance_value).text = diffStr
        holder.itemView.findViewById<TextView>(R.id.recycler_actual_value).text = CurrencyCalc.addSign(doubleFormat.format(actValue), defaultCurrency)
        holder.itemView.findViewById<TextView>(R.id.recycler_date).text = dataSet[position].cryptoDbInfo.date
        if (dataSet[position].expanded) {
            holder.myView.findViewById<LinearLayout>(R.id.recycler_details).visibility = View.VISIBLE
        } else {
            holder.myView.findViewById<LinearLayout>(R.id.recycler_details).visibility = View.GONE
        }
        holder.myView.setOnClickListener {
            dataSet[position].expanded = !dataSet[position].expanded
            notifyItemChanged(position)
        }
        holder.myView.findViewById<ImageView>(R.id.recycler_delete_ico).setOnClickListener {
            GlobalScope.launch {
                val db = Room.databaseBuilder(holder.myView.context,
                        AppDataListDatabase::class.java, "CryptoDataClassEntity.db"
                ).build()
                db.cryptocurrencyOwnedDao().deleteCrypto(dataSet[position].cryptoDbInfo.id)
                withContext(Dispatchers.Main) {
                    dataSet.removeAt(position)
                    notifyDataSetChanged()
                    notifyDataDelete.deleted()
                }
            }
        }
    }
}