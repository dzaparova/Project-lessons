package kg.tutorialapp.myweather


import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.widget.*

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.with
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kg.tutorialapp.myweather.models.CurrentForeCast
import kg.tutorialapp.myweather.models.ForeCast
import kg.tutorialapp.myweather.models.Weather
import kg.tutorialapp.myweather.network.WeatherClient
import kg.tutorialapp.myweather.storage.ForeCastDateBase
import kotlin.math.roundToInt
import kotlin.String as String

class MainActivity() : AppCompatActivity() {
    private val db by lazy {
        ForeCastDateBase.getInstance(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getWeatherFromApi()
        subscribeToLiveData()


    }




    private fun subscribeToLiveData() {

        val tv_temperature=findViewById<TextView>(R.id.tv_temperature)
        val tv_date=findViewById<TextView>(R.id.tv_date)
        val tv_temp_max=findViewById<TextView>(R.id.tv_temp_max)
        val tv_temp_min=findViewById<TextView>(R.id.tv_temp_min)
        val tv_feels_like=findViewById<TextView>(R.id.tv_feels_like)
        val tv_weather=findViewById<TextView>(R.id.tv_weather)
        val tv_sunsrise=findViewById<TextView>(R.id.tv_sunsrise)
        val tv_sunset=findViewById<TextView>(R.id.tv_sunset)
        val tv_humidity=findViewById<TextView>(R.id.tv_humidity)
        val iv_weather_icon =findViewById<ImageView>(R.id.iv_weather_icon)

        db.forecastDao().getAll().observe(this, Observer {
            it?.let {
                tv_temperature.text = it.current?.temp?.roundToInt().toString()
                tv_date.text=it.current?.date.format()
                tv_temp_max.text=it.daily?.get(0)?.temp?.max?.roundToInt()?.toString()
                tv_temp_min.text=it.daily?.get(0)?.temp?.min?.roundToInt()?.toString()
                tv_feels_like.text=it.current?.feels_like?.roundToInt()?.toString()
                tv_weather.text=it.current?.weather?.get(0)?.description
                tv_sunsrise.text=it.current?.sunrise?.format("hh:mm")
                tv_sunset.text=it.current?.sunset?.format("hh:mm")
                tv_humidity.text = "${it.current?.humidity?.toString()} %"
//                http://openweathermap.org/img/wn/10d@2x.png


                it.current?.weather?.get(0)?.icon?.let { icon ->
                    with(this)
                        .load( "https://openweathermap.org/img/wn/${icon}@2x.png")
                        .into(iv_weather_icon)
                }
            }
        })
    }


    @SuppressLint("CheckResult")
    private fun getWeatherFromApi() {
        WeatherClient.weatherApi.fetchWeather()
            .subscribeOn(Schedulers.io())
            .map {
                db.forecastDao().insert(it)
                it
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({    }, {
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
            })
    }


}







