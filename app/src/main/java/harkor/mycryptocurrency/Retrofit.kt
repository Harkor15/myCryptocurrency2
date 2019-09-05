package harkor.mycryptocurrency

import android.util.Log
import androidx.lifecycle.LiveData
import com.google.gson.annotations.SerializedName
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

internal const val UNKNOWN_CODE = -1

class RetrofitClientInstance {
    companion object {
        var instance: Retrofit = retrofit2.Retrofit.Builder()
                .baseUrl("https://api.coinpaprika.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataListAdapterFactory())
                .build()
    }
}

class LiveDataCallListAdapter<R>(private val responseType: Type) : CallAdapter<R, LiveData<ApiListResponse<R>>> {
    override fun adapt(call: Call<R>): LiveData<ApiListResponse<R>> {
        return object : LiveData<ApiListResponse<R>>() {
            private var isSucces = false

            override fun onActive() {
                super.onActive()
                if (!isSucces) enqueue()
            }

            override fun onInactive() {
                super.onInactive()
                dequeue()
            }

            private fun dequeue() {
                if (call.isExecuted) call.cancel()
            }

            private fun enqueue() {
                call.enqueue(object : Callback<R> {
                    override fun onFailure(call: Call<R>, t: Throwable) {
                        postValue(ApiListResponse.create(UNKNOWN_CODE, t))
                    }

                    override fun onResponse(call: Call<R>, response: Response<R>) {
                        postValue(ApiListResponse.create(response))
                    }
                })
            }


        }


    }

    override fun responseType(): Type = responseType
}

sealed class ApiListResponse<T> {
    companion object {
        fun <T> create(response: Response<T>): ApiListResponse<T> {
            return if (response.isSuccessful) {
                Log.d("MyCrypto",response.code().toString())
                val body = response.body()
                if (body == null || response.code() == 204) {
                    ApiEmptyResponse()
                } else {
                    ApiSuccessResponse(body)
                }
            } else {
                //Log.d("MyCrypto",response.code().toString())

                ApiErrorResponse(response.code(), response.errorBody()?.string()
                        ?: response.message())
            }
        }

        fun <T> create(errorCode: Int, error: Throwable): ApiErrorResponse<T> {
            return ApiErrorResponse(errorCode, error.message ?: "Unknown Error!")
        }
    }
}

class ApiEmptyResponse<T> : ApiListResponse<T>()
data class ApiErrorResponse<T>(val errorCode: Int, val errorMessage: String) : ApiListResponse<T>()
data class ApiSuccessResponse<T>(val body: T) : ApiListResponse<T>()

class LiveDataListAdapterFactory : CallAdapter.Factory() {
    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*, *>? {
        val observableType =
                CallAdapter.Factory.getParameterUpperBound(0, returnType as ParameterizedType) as? ParameterizedType
                        ?: throw IllegalAccessException("resource must be parametrized")
        return LiveDataCallListAdapter<Any>(CallAdapter.Factory.getParameterUpperBound(0, observableType))
    }
}

object Model {
    data class Cryptocurrency(val id: String, val name: String, val symbol: String)
}

interface CoinpaprikaApi {
    @GET("coins")
    fun getCoinsDataList(): LiveData<List<Model.Cryptocurrency>>

}

class CryptoDataList {
    @SerializedName("id")
    val id: String? = null
    @SerializedName("name")
    val name: String? = null
    @SerializedName("symbol")
    val symbol: String? = null
}
