package kg.tutorialapp.myweather.network

import kg.tutorialapp.myweather.models.ForeCast
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("onecall?lat=42.882004&lon=74.582748&exclude=minutely&appid=16ecd463571efaafe44dfb7d4f1f4a72&lang=ru&units=metric")
    fun getWeather(): Call<ForeCast>
    @GET("onecall")
    fun fetchWeatherUsingQuery(
        @Query("lat")lat:Double=42.882004,
        @Query("lon")lon:Double=74.582748,
        @Query("exclude")exclude:String="minutely",
        @Query("appid")appid:String="16ecd463571efaafe44dfb7d4f1f4a72",
        @Query("lang")lang:String="ru",
        @Query("units")units:String="metric"
    ):Call<ForeCast>

}