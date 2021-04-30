package io.github.mattpvaughn.uigradients.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import io.github.mattpvaughn.uigradients.data.local.model.Converters
import io.github.mattpvaughn.uigradients.data.local.model.Gradient


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


