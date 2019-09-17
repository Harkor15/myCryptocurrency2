package harkor.mycryptocurrency.services

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RequestInterface {

    @GET("api/v3/coins/list")
    fun getListData(): Observable<List<CryptoDataClassEntity>>

    @GET("api/v3/simple/price")
    fun getCryptoPrices(@Query("ids") ids: String,
                        @Query("vs_currencies") currencys: String): Observable<Map<String, Map<String, Double>>>
}

class RetrofitInstance {
    companion object {
        val requestInterface = Retrofit.Builder()
                .baseUrl("https://api.coingecko.com")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(RequestInterface::class.java)
    }
}