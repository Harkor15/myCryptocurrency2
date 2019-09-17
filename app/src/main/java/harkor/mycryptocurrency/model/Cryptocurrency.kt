package harkor.mycryptocurrency.model


import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup
import harkor.mycryptocurrency.CryptocurrencyOwnedEntity
import harkor.mycryptocurrency.Details

class Cryptocurrency(title: String, amount: Double?, items: List<Details>) : ExpandableGroup<Details>(title, items)

data class ActualPrices(var  usd: Double=0.0, var eur: Double=0.0, var pln: Double=0.0, var btc: Double=0.0)
data class CryptoFullInfo(val cryptoDbInfo: CryptocurrencyOwnedEntity, var actualPrices: ActualPrices, var expanded:Boolean=false )

object CurrencyCalc {
    fun addSign(amount:String, currencyID:Int):String{
        return when(currencyID){
            1-> "$$amount"
            2-> "${amount}€"
            3-> "${amount}zł"
            else-> "${amount}BTC"
        }
    }
}