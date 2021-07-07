package kg.tutorialapp.myweather.storage

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kg.tutorialapp.myweather.models.DailyForeCast
import kg.tutorialapp.myweather.models.HourlyForeCast

class CollectionsConverter {

    @TypeConverter
    fun fromHourlyForeCastListToJson(list:List<HourlyForeCast>?):String?=
        Gson().toJson(list)
    @TypeConverter
    fun fromJsonToHourlyFromCastList(json:String?):List<HourlyForeCast>? =
        Gson().fromJson(json,object : TypeToken<List<HourlyForeCast>>() {}.type)
    @TypeConverter
    fun fromDailyForeCastListToJson (list:List<DailyForeCast>?):String?=
        Gson().toJson(list)
    @TypeConverter
    fun fromJsonToDailyFromCastList(json:String?):List<DailyForeCast>? =
        Gson().fromJson(json,object : TypeToken<List<DailyForeCast>>() {}.type)
}
