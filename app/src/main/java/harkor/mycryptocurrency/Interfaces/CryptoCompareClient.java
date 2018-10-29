package harkor.mycryptocurrency.Interfaces;

import java.util.Map;
import harkor.mycryptocurrency.model.CryptoPrices;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface CryptoCompareClient {
    @GET("/data/pricemulti")
    Call<Map<String,CryptoPrices>> multiCryptoPrices(@Query("fsyms") String crypto,
                                                           @Query("tsyms") String currency);

    @GET("/data/price")
    Call<CryptoPrices> singleCryptoPrice(@Query("fsym") String crypto,
                                         @Query("tsyms") String currency);
}
