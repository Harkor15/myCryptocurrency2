package harkor.mycryptocurrency.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cryptocurrency")
data class CryptocurrencyData(
        @PrimaryKey var id: Int,
        var tag: String?,
        var amount: Double?,
        var date:String?,
        var priceUsd:Double?,
        var priceEur:Double?,
        var pricePln:Double?,
        var priceBtc:Double?
)