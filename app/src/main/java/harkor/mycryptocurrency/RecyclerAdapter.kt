package harkor.mycryptocurrency

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import androidx.annotation.IntegerRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_view_item.view.*
import kotlin.coroutines.coroutineContext

class RecyclerAdapter(var dataSet: ArrayList<CryptoFullInfo>) : RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder>() {

    class RecyclerViewHolder(var myView: View) : RecyclerView.ViewHolder(myView)

    private var greenColor: Int?=null
    private var redColor: Int?=null



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val myView = LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_view_item, parent, false)
        greenColor=ContextCompat.getColor(parent.context, R.color.light_green)
        redColor=ContextCompat.getColor(parent.context, R.color.light_red)

        return RecyclerViewHolder(myView)
    }

    override fun getItemCount() = dataSet.size

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        var defaultCurrency=SharedPref.getDefaultCurrency(holder.myView.context)
        var nowPrice:Double
        var historicalPrice:Double
        when (defaultCurrency){
            1 -> {
                nowPrice=dataSet[position].actualPrices.usd
                historicalPrice=dataSet[position].cryptoDbInfo.priceusd
            }
            2 ->{
                nowPrice=dataSet[position].actualPrices.eur
                historicalPrice=dataSet[position].cryptoDbInfo.priceeur
            }
            3 ->{
                nowPrice=dataSet[position].actualPrices.pln
                historicalPrice=dataSet[position].cryptoDbInfo.pricepln
            }
            else -> {
                nowPrice=dataSet[position].actualPrices.btc
                historicalPrice=dataSet[position].cryptoDbInfo.pricebtc
            }
        }
        val doubleFormat = java.text.DecimalFormat("0.00")
        val historicalPrices="${CurrencyCalc.addSign(doubleFormat.format(historicalPrice*dataSet[position].cryptoDbInfo.amount)
                ,defaultCurrency)} (${CurrencyCalc.addSign(doubleFormat.format(historicalPrice),defaultCurrency)})"
        holder.itemView.recycler_historical_prices.text=historicalPrices
        holder.itemView.recycler_tag.text=dataSet[position].cryptoDbInfo.symbol.toUpperCase()
        holder.itemView.recycler_amount.text=dataSet[position].cryptoDbInfo.amount.toString()
        holder.itemView.recycler_name.text=dataSet[position].cryptoDbInfo.name
        val actPrice="${dataSet[position].cryptoDbInfo.symbol.toUpperCase()} - ${CurrencyCalc.addSign(doubleFormat.format(nowPrice),defaultCurrency)}"
        holder.itemView.recycler_actual_price.text=actPrice
        val actValue=nowPrice*dataSet[position].cryptoDbInfo.amount
        val percentageDiff=(nowPrice*100)/historicalPrice-100
        var percentageDiffStr= doubleFormat.format(percentageDiff)+"%"
        if(percentageDiff>0){
            percentageDiffStr="+"+percentageDiffStr
            holder.myView.recycler_balance_percentage.setTextColor(greenColor!!)
            holder.myView.recycler_balance_value.setTextColor(greenColor!!)
        }else{
            holder.myView.recycler_balance_percentage.setTextColor(redColor!!)
            holder.myView.recycler_balance_value.setTextColor(redColor!!)
        }
        holder.itemView.recycler_balance_percentage.text=percentageDiffStr
        val diff=actValue-historicalPrice*dataSet[position].cryptoDbInfo.amount
        val diffStr="(${CurrencyCalc.addSign(doubleFormat.format(diff),defaultCurrency)})"
        holder.itemView.recycler_balance_value.text=diffStr
        holder.itemView.recycler_actual_value.text=CurrencyCalc.addSign(doubleFormat.format(actValue),defaultCurrency)
        holder.itemView.recycler_date.text=dataSet[position].cryptoDbInfo.date

        if (dataSet[position].expanded) {
            holder.myView.recycler_details.visibility = View.VISIBLE
        } else {
            holder.myView.recycler_details.visibility = View.GONE
        }
        holder.myView.setOnClickListener{
            dataSet[position].expanded=!dataSet[position].expanded
            notifyItemChanged(position)
            if(dataSet[position].expanded){
                animateExpand(holder.myView.recycler_arrow)
            }else{
                animateCollapse(holder.myView.recycler_arrow)
            }
        }
    }
    private fun animateExpand(arrow: ImageView) {
        val rotate = RotateAnimation(360f, 180f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        rotate.duration = 300
        rotate.fillAfter = true
        arrow.animation = rotate
    }

    private fun animateCollapse( arrow: ImageView) {
        val rotate = RotateAnimation(180f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        rotate.duration = 300
        rotate.fillAfter = true
        arrow.animation = rotate
    }

}