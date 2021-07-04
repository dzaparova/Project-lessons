package kg.tutorialapp.myweather

import kg.tutorialapp.myweather.network.PostApi
import kg.tutorialapp.myweather.network.WeatherApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object WeatherClient {
    private val okhhttp by lazy {
        val interceptor= HttpLoggingInterceptor().apply {
            level= HttpLoggingInterceptor.Level.BODY }
        OkHttpClient.Builder().addInterceptor(interceptor).build()

    }
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
//            .baseUrl("https://jsonplaceholder.typicode.com/")
//
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

            .addConverterFactory(GsonConverterFactory.create())
            .client(okhhttp)
            .build()
    }
    val weatherApi by lazy {

        retrofit.create(WeatherApi::class.java)
    }


}