package io.github.mattpvaughn.uigradients.data.local

import android.content.SharedPreferences
import io.github.mattpvaughn.uigradients.data.local.PrefsRepo.Companion.KEY_LAST_REFRESHED_DATA
import javax.inject.Inject
import javax.inject.Singleton

/** An interface for getting/setting persistent preferences */
interface PrefsRepo {
    var lastRefreshedDataEpoch: Long

    companion object {
        const val KEY_LAST_REFRESHED_DATA = "last refreshed"
        const val REFRESH_FREQUENCY_MINUTES = 60 * 24
    }
}

/** An implementation of [PrefsRepo] wrapping [SharedPreferences] */
@Singleton
class SharedPreferencesPrefsRepo @Inject constructor(private val sharedPreferences: SharedPreferences) :
    PrefsRepo {

    private val defaultLastRefreshedDataEpoch = 0L
    override var lastRefreshedDataEpoch: Long
        get() = sharedPreferences.getLong(KEY_LAST_REFRESHED_DATA, defaultLastRefreshedDataEpoch)
        set(value) = sharedPreferences.edit().putLong(KEY_LAST_REFRESHED_DATA, value).apply()
}
