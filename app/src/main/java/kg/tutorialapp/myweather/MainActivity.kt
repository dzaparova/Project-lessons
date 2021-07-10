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
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
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
import kotlin.String as String

class MainActivity() : AppCompatActivity() {
    private val db by lazy {
        ForeCastDateBase.getInstance(applicationContext)
    }
//    LiveData

//    private val liveData=MutableLiveData<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        makeRxCall()
//        getFromDb()

//        val button=findViewById<Button>(R.id.button)
////        SetValue ()  value = "",postValue()
//        button.setOnClickListener {
//            liveData.value="hello"
//
//        }

        val tv_forecast_list=findViewById<TextView>(R.id.tv_forecast_list)

        db.forecastDao().getAll().observe(this, Observer {
            tv_forecast_list.text=it?.toString()
        })

    }




    @SuppressLint("CheckResult")
    private fun makeRxCall() {
//        val tv_forecast_list=findViewById<TextView>(R.id.tv_forecast_list)
        WeatherClient.weatherApi.fetchWeather()
            .subscribeOn(Schedulers.io())
            .map {
                db.forecastDao().deleteAll()
                db.forecastDao().insert(it)
                it
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
//                tv_forecast_list.text=it.toString()
            }, {
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
            })
    }
//    @SuppressLint("CheckResult")
//    private fun getFromDb(){
//        val tv_forecast_list=findViewById<TextView>(R.id.tv_forecast_list)
//        db.forecastDao()
//            .getAll()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                       tv_forecast_list.text=it.toString()
//            },{
//
//            })
//
//    }


}







