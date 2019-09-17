package harkor.mycryptocurrency.services

object CurrencyCalc {
    fun addSign(amount: String, currencyID: Int): String {
        return when (currencyID) {
            1 -> "$$amount"
            2 -> "${amount}€"
            3 -> "${amount}zł"
            else -> "${amount}BTC"
        }
    }
}