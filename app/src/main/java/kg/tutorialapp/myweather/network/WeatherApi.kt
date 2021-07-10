package kg.tutorialapp.myweather.network

import io.reactivex.Observable
import io.reactivex.Single
import kg.tutorialapp.myweather.models.ForeCast
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("onecall")
    fun fetchWeather(
        @Query("lat")lat:Double = 42.882004,
        @Query("lon")lon:Double = 74.582748,
        @Query("exclude") exclude:String="minutely",
        @Query("appid") appid:String="16ecd463571efaafe44dfb7d4f1f4a72",
        @Query("lang")lang:String="ru",
        @Query("units")units:String="metric"
    ):Single<ForeCast>

}