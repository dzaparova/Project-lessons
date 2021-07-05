package kg.tutorialapp.myweather.storage

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kg.tutorialapp.myweather.models.CurrentForeCast
import kg.tutorialapp.myweather.models.ForeCast

class ModelsConverter {
    fun fromCurrentForeCastToJson(forecast:CurrentForeCast):String=
        Gson().toJson(forecast)
    fun fromJsonToCurrentForeCast(json:String):ForeCast=
        Gson().fromJson(json,object :TypeToken<CurrentForeCast>(){}.type)
}