package io.github.mattpvaughn.uigradients.data.local.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.android.parcel.Parcelize
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Models a gradient as a list of colors and additional info
 *
 * In a real life scenario, we'd break up the network and local models so they aren't coupled with
 * each other, but they're identical for this basic use-case
 */
@JsonClass(generateAdapter = true)
@Parcelize
@Entity
data class Gradient(
    @PrimaryKey val name: String, val colors: List<String> = emptyList()
) : Parcelable

/**
 * A [TypeConverter] to aid in persisting [List<String>]s
 */
@ProvidedTypeConverter
@Singleton
class Converters @Inject constructor(moshi: Moshi) {

    private val stringListType = Types.newParameterizedType(List::class.java, String::class.java)
    private val adapter = moshi.adapter<List<String>>(stringListType)

    @TypeConverter
    fun fromString(value: String) = adapter.fromJson(value) ?: emptyList()

    @TypeConverter
    fun fromArrayList(strings: List<String>) = adapter.toJson(strings)
}