package harkor.mycryptocurrency


import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup

class Cryptocurrency(title: String, amount: Double?, items: List<Details>) : ExpandableGroup<Details>(title, items)

data class ActualPrices(var  usd: Double=0.0, var eur: Double=0.0, var pln: Double=0.0, var btc: Double=0.0)
data class CryptoFullInfo(val cryptoDbInfo: CryptocurrencyOwnedEntity, var actualPrices: ActualPrices )