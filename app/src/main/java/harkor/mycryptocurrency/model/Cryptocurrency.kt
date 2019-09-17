package harkor.mycryptocurrency.model


import harkor.mycryptocurrency.services.CryptocurrencyOwnedEntity

data class ActualPrices(var usd: Double = 0.0, var eur: Double = 0.0, var pln: Double = 0.0, var btc: Double = 0.0)
data class CryptoFullInfo(val cryptoDbInfo: CryptocurrencyOwnedEntity, var actualPrices: ActualPrices, var expanded: Boolean = false)

