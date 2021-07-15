package kg.tutorialapp.myweather.storage

import androidx.lifecycle.LiveData
import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single
import kg.tutorialapp.myweather.models.ForeCast

@Dao
interface ForeCastDao {
//    Insert Update Delete Query
   @Insert(onConflict = OnConflictStrategy.REPLACE)
   fun insert(forecast:ForeCast)
   @Update
   fun update(forecast: ForeCast):Completable
   @Delete
   fun delete(forecast: ForeCast):Completable

   @Query("select * from ForeCast")
   fun getAll(): LiveData<ForeCast>

   @Query("select * from ForeCast where id=:id" )
   fun getById(id:Long):Single<ForeCast>

   @Query("delete from ForeCast")
   fun deleteAll():Completable
}


