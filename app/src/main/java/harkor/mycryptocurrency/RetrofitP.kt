package harkor.mycryptocurrency

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


data class CryptoListData (val id: String, val name: String , val symbol: String)

interface RequestInterface{
    @GET("v1/coins")
    fun getListData(): Observable<List<CryptoDataClassEntity>>
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