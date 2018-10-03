package harkor.mycryptocurrency.services;

import android.util.Log;

import java.util.Collection;
import java.util.Map;

import harkor.mycryptocurrency.CryptoCompareClient;
import harkor.mycryptocurrency.SingleResponseForAdvanced;
import harkor.mycryptocurrency.model.CryptoPrices;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RetrofitInterface {

    Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();
    private static final String CURRENCY="USD,EUR,PLN,BTC";

    public void actualPrice(String name, final int currencyCode, final SingleResponseForAdvanced srfa){
        CryptoCompareClient client=retrofit.create(CryptoCompareClient.class);
        Call<CryptoPrices> call= client.singleCryptoPrice(name,CURRENCY);
        call.enqueue(new Callback<CryptoPrices>() {
            @Override
            public void onResponse(Call<CryptoPrices> call, Response<CryptoPrices> response) {
                CryptoPrices cryptoPrices=response.body();
                double result=0;
                switch (currencyCode){
                    case 1:result=cryptoPrices.getPriceUSD();break;
                    case 2:result=cryptoPrices.getPriceEUR();break;
                    case 3:result=cryptoPrices.getPricePLN();break;
                    case 4:result=cryptoPrices.getPriceBTC();break;
                }
                srfa.actualPrice(result);
            }

            @Override
            public void onFailure(Call<CryptoPrices> call, Throwable t) {
                srfa.wentWrong();
            }
        });
    }

    public void singleCrypto( final CryptoCheckAdd cryptoCheckAdd){
        CryptoCompareClient client=retrofit.create(CryptoCompareClient.class);
        Call<CryptoPrices> call= client.singleCryptoPrice(cryptoCheckAdd.name,CURRENCY);
        call.enqueue(new Callback<CryptoPrices>() {
            @Override
            public void onResponse(Call<CryptoPrices> call, Response<CryptoPrices> response) {
                CryptoPrices cryptoPrices=response.body();
                cryptoCheckAdd.add(cryptoPrices);
            }

            @Override
            public void onFailure(Call<CryptoPrices> call, Throwable t) {
                cryptoCheckAdd.retrofitError(t);
            }
        });
    }
    public void multiCrypto(String names){
        CryptoCompareClient client=retrofit.create(CryptoCompareClient.class);
        Call<Map<String,CryptoPrices>> call=client.multiCryptoPrices(names,CURRENCY);
        call.enqueue(new Callback<Map<String,CryptoPrices>>() {
            @Override
            public void onResponse(Call<Map<String,CryptoPrices>> call, Response<Map<String,CryptoPrices>> response) {
                Map<String,CryptoPrices> map=response.body();
                Collection<CryptoPrices> col=map.values();
                CryptoPrices cP=map.get("BTC");
                Log.d("MyCrypto", "succes retrof "+cP.getPriceUSD()  );
            }

            @Override
            public void onFailure(Call<Map<String,CryptoPrices>> call, Throwable t) {
                Log.d("MyCrypto", "fail retrof "+t);
            }
        });
    }
}
