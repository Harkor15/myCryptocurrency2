package harkor.mycryptocurrency

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


//data class PriceInfo(val usd: Double,val pln:Double, val btc:Double, val eur:Double)
//data class BTC (val price:String) data class PLN (val price:String)
//data class EUR (val price:String) data class USD (val price:String)
//data class Quotes(val BTC:BTC, val USD:USD, val EUR:EUR, val PLN:PLN )
//data class CryptocurrencyInfo (val id:String, val name:String, val symbol:String,val  quotes:Quotes )

interface RequestInterface{

    @GET("api/v3/coins/list")
    fun getListData(): Observable<List<CryptoDataClassEntity>>

    @GET("api/v3/simple/price")
    fun getCryptoPrices(@Query("ids") ids:String, @Query("vs_currencies") currencys:String ): Observable<Map<String,Map<String,Double>>>

}

class RetrofitInstance{
    companion object {
        val requestInterface = Retrofit.Builder()
                .baseUrl("https://api.coingecko.com")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(RequestInterface::class.java)
    }
}