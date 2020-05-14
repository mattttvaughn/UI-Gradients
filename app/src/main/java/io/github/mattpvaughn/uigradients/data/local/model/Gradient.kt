package io.github.mattpvaughn.uigradients.data.local.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

// In a real life scenario, break up the network and local models so they aren't coupled with
// each other
@JsonClass(generateAdapter = true)
@Parcelize
@Entity
data class Gradient(
    @PrimaryKey val name: String, val colors: List<String> = emptyList()
) : Parcelable

class Converters {
    @TypeConverter
    fun fromString(value: String) : List<String> {
        return value.split("|")
    }

    @TypeConverter
    fun fromArrayList(strings: List<String>): String {
        return strings.joinToString("|")
    }
}