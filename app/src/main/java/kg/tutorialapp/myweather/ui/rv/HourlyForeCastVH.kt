package kg.tutorialapp.myweather.ui.rv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kg.tutorialapp.myweather.Extensions.format
import kg.tutorialapp.myweather.R
import kg.tutorialapp.myweather.models.Constants
import kg.tutorialapp.myweather.models.HourlyForeCast
import kotlin.math.roundToInt

class HourlyForeCastVH(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun bind(item: HourlyForeCast) {
        itemView.run {
            val tv_time = itemView.findViewById<TextView>(R.id.tv_time)
            tv_time.text = item.date?.format("HH:mm")

            val tv_precipitation = itemView.findViewById<TextView>(R.id.tv_precipitation)
            item.probability?.let {
                tv_precipitation.text = "${(it * 100).roundToInt()}%"
            }

            val tv_temp =itemView.findViewById<TextView>(R.id.tv_temp)
            tv_temp.text = item.temp?.roundToInt()?.toString()

            val iv_weather_icon = itemView.findViewById<ImageView>(R.id.iv_weather_icon)
            Glide.with(itemView.context)
                .load("${Constants.iconUri}${item.weather?.get(0)?.icon}${Constants.iconFormat}")
                .into(iv_weather_icon)
        }
    }

    companion object{
        fun create(parent: ViewGroup): HourlyForeCastVH {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_hourly_forecast, parent, false)
            return  HourlyForeCastVH(view)
        }
    }
}