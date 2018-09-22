package harkor.mycryptocurrency.services;

import java.util.List;

import harkor.mycryptocurrency.CryptoCompareClient;
import harkor.mycryptocurrency.model.CryptoPrices;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {

    private static Retrofit retrofit;
    private static final String  BASE_URL = "https://min-api.cryptocompare.com";

    public static Retrofit getRetrofitInstance(){
        if(retrofit==null){
            retrofit= new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
