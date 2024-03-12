package it.icemangp.fastapp.ui.main

import com.squareup.moshi.Moshi
import it.icemangp.shakenetworklog.data.NetworkLogInterceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

object SampleRepository {

    data class Repo(val id: String)

    interface StressTestRepo {
        @GET("/search?")
        fun search(@Query("q") user: String?): Call<String>
        @GET("/searczzh?")
        fun searchFail(@Query("q") user: String?): Call<String>
    }

    val retrofit: Retrofit
    val service: StressTestRepo


    init {
        val connectionTimeout = 30L
        val readTimeout = 30L

        val networkLogInterceptor = NetworkLogInterceptor()

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(networkLogInterceptor)
            .connectTimeout(connectionTimeout, TimeUnit.SECONDS) // connect timeout
            .readTimeout(readTimeout, TimeUnit.SECONDS)    // socket timeout
            .build()

        val moshiBuilder = Moshi.Builder().build()

        val converterFactory = MoshiConverterFactory.create(moshiBuilder)//.withNullSerialization()

        retrofit = Retrofit.Builder()
            .baseUrl("https://www.google.it/"/*BuildConfig.API_BASE_URL*/)
            .addConverterFactory(converterFactory)
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(okHttpClient)
            .build()

        service = retrofit.create(StressTestRepo::class.java)
    }

    fun sampleNetworkCall(word: String): Response<String>? {
        return try {
            service.search(word).execute()
        }
        catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun sampleFailNetworkCall(word: String): Response<String>? {
        return try {
            service.searchFail(word).execute()
        }
        catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}