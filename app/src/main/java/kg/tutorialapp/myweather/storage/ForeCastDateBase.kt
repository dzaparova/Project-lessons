package kg.tutorialapp.myweather.storage

import android.content.Context
import androidx.room.*
import kg.tutorialapp.myweather.models.ForeCast

@Database(
    entities = [ForeCast::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(ModelsConverter ::class, CollectionsConverter::class)
abstract class ForeCastDateBase: RoomDatabase() {
    abstract fun forecastDao():ForeCastDao


    companion object{
        const val DB_NAME="foreCast_DB"
        private var DB:ForeCastDateBase?=null

        fun getInstance(context: Context):ForeCastDateBase{
            if(DB==null){
                DB= Room.databaseBuilder(
                    context,
                    ForeCastDateBase::class.java,
                    DB_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return DB!!
        }
    }

}