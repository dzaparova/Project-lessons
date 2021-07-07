package kg.tutorialapp.myweather


import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.View

import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kg.tutorialapp.myweather.models.CurrentForeCast
import kg.tutorialapp.myweather.models.ForeCast
import kg.tutorialapp.myweather.models.Weather
import kg.tutorialapp.myweather.network.WeatherClient
import kg.tutorialapp.myweather.storage.ForeCastDateBase
import kotlin.String as String

class MainActivity() : AppCompatActivity() {
    private val db by lazy {
        ForeCastDateBase.getInstance(applicationContext)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setup()
    }

    private fun setup() {
        val btn_insert=findViewById<Button>(R.id.btn_insert)
        val btn_update=findViewById<Button>(R.id.btn_update)
        val btn_delete=findViewById<Button>(R.id.btn_delete)
        val btn_query=findViewById<Button>(R.id.btn_query)
        val btn_query_get_all=findViewById<Button>(R.id.btn_query_get_all)
        val tv_forecast_list=findViewById<TextView>(R.id.tv_forecast_list)

        btn_insert.setOnClickListener { it: View? ->
             db.forecastDao()
                .insert(getForecastFromInput())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {}
        }
        btn_update.setOnClickListener { it: View? ->
             db.forecastDao()
                .update(getForecastFromInput())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {}
        }
        btn_delete.setOnClickListener { it: View? ->
            db.forecastDao()
                .delete(getForecastFromInput())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {}
        }
        btn_query_get_all.setOnClickListener { it: View? ->
            db.forecastDao()
                .getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe (
                    {
                        var text=""
                        it.forEach{
                            text+=it.toString()
                        }
                        tv_forecast_list.text=text
                    }
                    ,{

                    })
        }

        btn_query.setOnClickListener { it: View? ->
            db.forecastDao()
//                .getById(14L)
                .deleteAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe (
                    {

                    }
                    ,{

                    })
        }
    }

    private fun getForecastFromInput(): ForeCast {
        val ed_id=findViewById<EditText>(R.id.ed_id)
        val ed_lat=findViewById<EditText>(R.id.ed_lat)
        val ed_long=findViewById<EditText>(R.id.ed_long)
        val ed_description=findViewById<EditText>(R.id.ed_description)

        val id = ed_id.text?.toString().takeIf { !it.isNullOrEmpty() } ?.toLong()
        val lat= ed_lat.text?. toString(). takeIf { !it.isNullOrEmpty() } ?.toDouble()
        val long =ed_long.text?. toString(). takeIf { !it.isNullOrEmpty() } ?.toDouble()
        val description=ed_description?.text.toString()
        val current=CurrentForeCast(weather = listOf(Weather(description=description)))
        return ForeCast(id=id,lat=lat,lon=long,current = current)
    }

    







    @SuppressLint("CheckResult")
    private fun makeRxCall() {
        WeatherClient.weatherApi.fetchWeather()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            }, {
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
            })
    }


}



