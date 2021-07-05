package kg.tutorialapp.myweather.storage

import androidx.room.Dao
import androidx.room.Insert
import kg.tutorialapp.myweather.models.ForeCast

@Dao
interface ForeCastDao {
   @Insert
   fun insert(forecast:ForeCast)
}


