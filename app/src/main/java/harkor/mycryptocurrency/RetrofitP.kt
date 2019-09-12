package harkor.mycryptocurrency

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


//data class CryptoListData (val id: String, val name: String , val symbol: String)
data class BTC (val price:String) data class PLN (val price:String)
data class EUR (val price:String) data class USD (val price:String)
data class Quotes(val BTC:BTC, val USD:USD, val EUR:EUR, val PLN:PLN )
data class CryptocurrencyInfo (val id:String, val name:String, val symbol:String,val  quotes:Quotes )

interface RequestInterface{

    @GET("v1/coins")
    fun getListData(): Observable<List<CryptoDataClassEntity>>

    @GET("v1/tickers/{cryptoID}?quotes=BTC,USD,EUR,PLN")
    fun getCryptoPrices(@Path("cryptoID") cryptoID:String): Observable<CryptocurrencyInfo>

}

class RetrofitInstance{
    companion object {
        val requestInterface = Retrofit.Builder()
                .baseUrl("https://api.coinpaprika.com")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(RequestInterface::class.java)
    }
}