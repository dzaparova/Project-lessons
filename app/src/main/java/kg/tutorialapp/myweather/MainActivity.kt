package kg.tutorialapp.myweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
    private val okhhttp by lazy {
        val interceptor= HttpLoggingInterceptor().apply {
            level=HttpLoggingInterceptor.Level.BODY }
        OkHttpClient.Builder().addInterceptor(interceptor).build()

    }
    private val retrofit by lazy {
        Retrofit.Builder()
//            .baseUrl("https://api.openweathermap.org/data/2.5/")
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhhttp)
                .build()
    }
    private val weatherApi by lazy {

        retrofit.create(WeatherApi::class.java)
    }
    private val postsApi by lazy {
        retrofit.create(PostApi::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        fetchWeather()
//        fetchWeatherUsingQuery()
//        fetchPostById()
//        createPost()
//        createPostUsingFields()
//        createPostUsingFieldMap()
//        updatePost()
        deletePost()
    }

    private fun deletePost(){
        val call=postsApi.deletePost("42")
        val textView1 = findViewById<TextView>(R.id.textView1)
        call.enqueue(object :Callback<Unit>{
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                textView1.text=response.code().toString()

            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {

            }
        })
    }

    private fun updatePost() {
        val newPost=Post(userId = "20",body = "this is body")
        val textView1 = findViewById<TextView>(R.id.textView1)
        val call=postsApi.patchPost(id="42",post = newPost)
        call.enqueue(object :Callback<Post> {

            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                val resultPost = response.body()
                resultPost?.let {
                    val resultText = "ID:" + it.id + "\n" +
                            "UserId:" + it.userId + "\n" +
                            "TITLE:" + it.title + " \n" +
                            "BODY:" + it.body + "\n"

                    textView1.text = resultText

                }
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {

            }
        })
    }

    private fun createPostUsingFieldMap() {
        val map =HashMap<String,String>().apply {
            put("userId","55")
            put("title","SUPP!!")
            put("body","Karakol")
        }
        val call=postsApi.createPostUsingFieldMap(map)
        val textView1 = findViewById<TextView>(R.id.textView1)

        call.enqueue(object :Callback<Post>{

            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                val resultPost=response.body()
                resultPost?.let {
                    val resultText="ID:"+it.id+ "\n"+
                            "UserId:"+it.userId + "\n"+
                            "TITLE:" + it.title +" \n"+
                            "BODY:" + it.body + "\n"

                    textView1.text=resultText

                }
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {

            }

        })
    }

    private fun createPostUsingFields() {

        val call=postsApi.createPostUsingFields(userId = 99,title = "HI!!",body = "OSH")
        val textView1 = findViewById<TextView>(R.id.textView1)

        call.enqueue(object :Callback<Post>{

            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                val resultPost=response.body()
                resultPost?.let {
                    val resultText="ID:"+it.id+ "\n"+
                            "UserId:"+it.userId + "\n"+
                            "TITLE:" + it.title +" \n"+
                            "BODY:" + it.body + "\n"

                    textView1.text=resultText

                }
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {

            }

        })
    }

    private fun createPost() {
        val post=Post(userId = "42",title="Hello",body = "Bishkek")
        val call=postsApi.createPost(post)
        val textView1 = findViewById<TextView>(R.id.textView1)

        call.enqueue(object :Callback<Post>{

            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                val resultPost=response.body()
                resultPost?.let {
                    val resultText="ID:"+it.id+ "\n"+
                            "UserId:"+it.userId + "\n"+
                            "TITLE:" + it.title +" \n"+
                            "BODY:" + it.body + "\n"

                    textView1.text=resultText

                }
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {

            }

        })
    }

    private fun fetchPostById() {
        val textView1 = findViewById<TextView>(R.id.textView1)
        val call = postsApi.fetchById(10)
        call.enqueue(object :Callback<Post>{

            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                val post=response.body()
                post?.let {
                    val resultText="ID:"+it.id+ "\n"+
                            "UserId:"+it.userId + "\n"+
                            "TITLE:" + it.title +" \n"+
                            "BODY:" + it.body + "\n"

                    textView1.text=resultText

                }
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {

            }

        })
    }


    private fun fetchWeatherUsingQuery( ){
        val call = weatherApi.fetchWeatherUsingQuery(lat=40.513996,lon=72.816101)
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


    private fun fetchWeather() {
        val call = weatherApi.getWeather()
        val textView1 = findViewById<TextView>(R.id.textView1)
        val textView2 = findViewById<TextView>(R.id.textView2)


        call.enqueue(object : Callback<ForeCast> {
            override fun onResponse(call: Call<ForeCast>, response: Response<ForeCast>) {
                if (response.isSuccessful) {
                    val foreCast = response.body()
                    foreCast?.let {
                        textView1.text = it.current?.weather!![0].description
                        textView2.text = it.current?.temp.toString()
                        Toast.makeText(this@MainActivity, it.toString(), Toast.LENGTH_LONG).show()
                    }

                }

            }

            override fun onFailure(call: Call<ForeCast>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_LONG).show()
            }

        })
    }
}