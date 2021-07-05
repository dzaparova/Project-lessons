package kg.tutorialapp.myweather


import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kg.tutorialapp.myweather.models.ForeCast
import kg.tutorialapp.myweather.models.Post
import kg.tutorialapp.myweather.network.PostApi
import kg.tutorialapp.myweather.network.WeatherApi
import kg.tutorialapp.myweather.storage.ForeCastDao
import kg.tutorialapp.myweather.storage.ForeCastDateBase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.Flow
import kotlin.String as String

class MainActivity : AppCompatActivity() {


//    val example=SingleTonExample.getInstance()
    private var workResult=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        fetchWeatherUsingQuery()
        setup()
//
    }

    private fun setup() {
        val btn_start=findViewById<Button>(R.id.btn_start)
        val btn_show=findViewById<Button>(R.id.btn_showToast)
        btn_start.setOnClickListener {
//            doSomeWork()
            makeRxCall()
        }
        btn_show.setOnClickListener {
            Toast.makeText(this,"HELLO",Toast.LENGTH_LONG).show()
            ForeCastDateBase.getInstance(applicationContext).forecastDao().insert(ForeCast(lat=52255.515))
        }

    }


    @SuppressLint("CheckResult")
    private fun makeRxCall() {
        val textView1 = findViewById<TextView>(R.id.textView1)
        val textView2 = findViewById<TextView>(R.id.textView2)

        WeatherClient.weatherApi.fetchWeather()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({
                    textView1.text = it.current?.weather!![0].description
                    textView2.text = it.current?.temp.toString()

                },{ Toast.makeText(this,it.message,Toast.LENGTH_LONG).show()
                })


    }

    //    just,create,fromCallable(),fromIterable()
//    disposable,compositeDisposable
    private fun doSomeWork() {
    val observable = Observable.create<String> { emitter ->
        Log.d(TAG,"${Thread.currentThread().name} Starting emitting() ")
        Thread.sleep(1000)
        emitter.onNext("hello")
        Thread.sleep(3000)
        emitter.onNext("Bishkek")
        emitter.onComplete()
    }
   val observer=object:Observer<String> {

       override fun onSubscribe(d: Disposable) {

       }

       override fun onNext(t: String) {
            Log.d(TAG,"${Thread.currentThread().name}onNext() $t")
       }

       override fun onError(e: Throwable) {

       }

       override fun onComplete() {

       }



   }
    observable
            .subscribeOn(Schedulers.computation())
            .map{
                Log.d(TAG,"${Thread.currentThread().name} Starting mapping() ")
                it.toUpperCase()
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }



//        val tv_counter=findViewById<TextView>(R.id.counter)
//        for (i in 0..4) {
//                Thread.sleep(1000)
//                workResult++
//            }
//        tv_counter.text=workResult.toString()
    //        Thread(Runnable {
//            for (i in 0..4) {
//                Thread.sleep(1000)
//                workResult++
//            }
//            runOnUiThread{
//                tv_counter.text=workResult.toString()
//            }
////            Handler(Looper.getMainLooper()).post(Runnable {
////                tv_counter.text=workResult.toString()
////            })
//        }).start()


//    private fun fetchWeatherUsingQuery() {
//        val call = WeatherClient.weatherApi.fetchWeatherUsingQuery(lat = 40.513996, lon = 72.816101)
//        val textView1 = findViewById<TextView>(R.id.textView1)
//        val textView2 = findViewById<TextView>(R.id.textView2)
//        call.enqueue(object : Callback<ForeCast> {
//            override fun onResponse(call: Call<ForeCast>, response: Response<ForeCast>) {
//                if (response.isSuccessful) {
//                    val foreCast = response.body()
//                    foreCast?.let {
//                        textView1.text = it.current?.weather!![0].description
//                        textView2.text = it.current?.temp.toString()
////                        Toast.makeText(this@MainActivity, it.toString(), Toast.LENGTH_LONG).show()
//                    }
//
//                }
//
//            }
//
//            override fun onFailure(call: Call<ForeCast>, t: Throwable) {
//                Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_LONG).show()
//            }
//
//        })
//    }
    companion object{
        const val TAG="RX"
    }
}
