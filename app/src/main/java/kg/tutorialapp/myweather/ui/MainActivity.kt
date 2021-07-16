package kg.tutorialapp.myweather.ui


import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.with
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kg.tutorialapp.myweather.Extensions.format
import kg.tutorialapp.myweather.R
import kg.tutorialapp.myweather.models.Constants
import kg.tutorialapp.myweather.models.ForeCast
import kg.tutorialapp.myweather.network.WeatherClient
import kg.tutorialapp.myweather.storage.ForeCastDateBase
import kg.tutorialapp.myweather.ui.rv.DailyForeCastAdapter
import kotlin.math.roundToInt

class MainActivity() : AppCompatActivity() {
    private val db by lazy {
        ForeCastDateBase.getInstance(applicationContext)
    }
    private lateinit var dailyForeCastAdapter: DailyForeCastAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViews()
        setupRecyclerView()
        getWeatherFromApi()
        subscribeToLiveData()
    }


    private fun setupViews() {
        val tv_refresh=findViewById<TextView>(R.id.tv_refresh)
        tv_refresh.setOnClickListener {
            showLoading()
            getWeatherFromApi()

        }
    }
    private fun setupRecyclerView() {
        val rv_daily_forecast=findViewById<RecyclerView>(R.id.rv_daily_forecast)
        dailyForeCastAdapter= DailyForeCastAdapter()
        rv_daily_forecast.adapter=dailyForeCastAdapter
    }

    private fun showLoading() {
        val progress=findViewById<ProgressBar>(R.id.progress)

        progress.visibility= View.VISIBLE
    }
    private fun hideLoading(){
        val progress=findViewById<ProgressBar>(R.id.progress)

        progress.visibility= View.GONE

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
            .subscribe({
                hideLoading()
            }, {
                hideLoading()
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
            })
    }

    private fun subscribeToLiveData() {
        db.forecastDao().getAll().observe(this, Observer {
            it?.let {
                setValuesToViews(it)
                loadWeatherIcon(it)
                it.daily?.let { dailyList->
                    dailyForeCastAdapter.setItem(dailyList)
                }
            }
        })
    }

    private fun setValuesToViews(it:ForeCast) {
        val tv_temperature=findViewById<TextView>(R.id.tv_temperature)
        val tv_date=findViewById<TextView>(R.id.tv_date)
        val tv_temp_max=findViewById<TextView>(R.id.tv_temp_max)
        val tv_temp_min=findViewById<TextView>(R.id.tv_temp_min)
        val tv_feels_like=findViewById<TextView>(R.id.tv_feels_like)
        val tv_weather=findViewById<TextView>(R.id.tv_weather)
        val tv_sunsrise=findViewById<TextView>(R.id.tv_sunsrise)
        val tv_sunset=findViewById<TextView>(R.id.tv_sunset)
        val tv_humidity=findViewById<TextView>(R.id.tv_humidity)


        tv_temperature.text = it.current?.temp?.roundToInt().toString()
        tv_date.text = it.current?.date.format()
        tv_temp_max.text = it.daily?.get(0)?.temp?.max?.roundToInt()?.toString()
        tv_temp_min.text = it.daily?.get(0)?.temp?.min?.roundToInt()?.toString()
        tv_feels_like.text = it.current?.feels_like?.roundToInt()?.toString()
        tv_weather.text = it.current?.weather?.get(0)?.description
        tv_sunsrise.text = it.current?.sunrise?.format("HH:mm")
        tv_sunset.text = it.current?.sunset?.format("HH:mm")
        tv_humidity.text = "${it.current?.humidity?.toString()} %"
    }

    private fun loadWeatherIcon(it: ForeCast) {
        val iv_weather_icon =findViewById<ImageView>(R.id.iv_weather_icon)
        it.current?.weather?.get(0)?.icon?.let { icon ->

            Glide.with(this)
                .load("${Constants.iconUri}${icon}${Constants.iconFormat}")
                .into(iv_weather_icon)
        }
    }



}





