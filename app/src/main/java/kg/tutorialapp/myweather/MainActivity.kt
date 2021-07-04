package kg.tutorialapp.myweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kg.tutorialapp.myweather.models.ForeCast
import kg.tutorialapp.myweather.models.Post
import kg.tutorialapp.myweather.network.PostApi
import kg.tutorialapp.myweather.network.WeatherApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
            doSomeWork()
        }
        btn_show.setOnClickListener {
            Toast.makeText(this,"HELLO",Toast.LENGTH_LONG).show()
        }

    }
    private fun doSomeWork(){
        val tv_counter=findViewById<TextView>(R.id.counter)

        for (i in 0..4) {
                Thread.sleep(1000)
                workResult++
            }

        tv_counter.text=workResult.toString()

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
    }

    private fun fetchWeatherUsingQuery() {
        val call = WeatherClient.weatherApi.fetchWeatherUsingQuery(lat = 40.513996, lon = 72.816101)
        val textView1 = findViewById<TextView>(R.id.textView1)
        val textView2 = findViewById<TextView>(R.id.textView2)


        call.enqueue(object : Callback<ForeCast> {
            override fun onResponse(call: Call<ForeCast>, response: Response<ForeCast>) {
                if (response.isSuccessful) {
                    val foreCast = response.body()
                    foreCast?.let {
                        textView1.text = it.current?.weather!![0].description
                        textView2.text = it.current?.temp.toString()
//                        Toast.makeText(this@MainActivity, it.toString(), Toast.LENGTH_LONG).show()
                    }

                }

            }

            override fun onFailure(call: Call<ForeCast>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_LONG).show()
            }

        })
    }
}
