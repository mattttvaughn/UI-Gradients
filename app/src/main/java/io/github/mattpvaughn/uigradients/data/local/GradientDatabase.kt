package io.github.mattpvaughn.uigradients.data.local

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import io.github.mattpvaughn.uigradients.data.local.model.Converters
import io.github.mattpvaughn.uigradients.data.local.model.Gradient


private const val MODEL_DATABASE_NAME = "model_db"

private lateinit var INSTANCE: GradientDatabase
fun getGradientDatabase(context: Context): GradientDatabase {
    synchronized(GradientDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext, GradientDatabase::class.java, MODEL_DATABASE_NAME
            ).build()
        }
    }
    return INSTANCE
}

@Database(entities = [Gradient::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class GradientDatabase : RoomDatabase() {
    abstract val gradientDao: GradientDao
}

@Dao
interface GradientDao {
    @Query("SELECT * FROM Gradient")
    fun getAllRows(): LiveData<List<Gradient>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(rows: List<Gradient>)

    @Query("DELETE FROM Gradient WHERE :name = name")
    fun delete(name: String)

    @Query("DELETE FROM Gradient")
    fun clear()
}


