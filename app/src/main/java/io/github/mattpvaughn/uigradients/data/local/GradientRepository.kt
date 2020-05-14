package io.github.mattpvaughn.uigradients.data.local

import android.text.format.DateFormat
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import io.github.mattpvaughn.uigradients.application.APP_NAME
import io.github.mattpvaughn.uigradients.application.SECONDS_PER_MINUTE
import io.github.mattpvaughn.uigradients.data.local.PrefsRepo.Companion.REFRESH_FREQUENCY_MINUTES
import io.github.mattpvaughn.uigradients.data.local.model.Gradient
import io.github.mattpvaughn.uigradients.data.remote.UIGradientsApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GradientRepository @Inject constructor(
    private val prefsRepo: PrefsRepo,
    private val gradientDao: GradientDao,
    private val uiGradientsApiService: UIGradientsApiService
) {

    suspend fun updateData() {
        val currentEpoch = System.currentTimeMillis()
        val sdf = java.text.SimpleDateFormat("HH:mm:ss")
        val currDate = java.util.Date(currentEpoch)
        val lastDate = java.util.Date(prefsRepo.lastRefreshedDataEpoch)
        val curr = sdf.format(currDate)
        val last = sdf.format(lastDate)
        Log.i(APP_NAME, "Current = $curr, last = $last")
        /**
         * We only want to refresh the data every [REFRESH_FREQUENCY_MINUTES] minutes, because
         * we do not expect the source data to be updated frequently
         */
        if (currentEpoch - prefsRepo.lastRefreshedDataEpoch > REFRESH_FREQUENCY_MINUTES * 60 * 1000) {
            Log.i(APP_NAME, "UPDATED")
            prefsRepo.lastRefreshedDataEpoch = currentEpoch
            withContext(Dispatchers.IO) {
                val gradients = uiGradientsApiService.fetchGradientsList()
                insertAll(gradients)
            }
        }
    }

    fun getAllModels(): LiveData<List<Gradient>> {
        return gradientDao.getAllRows()
    }

    fun insertAll(gradients: List<Gradient>) {
        return gradientDao.insertAll(gradients)
    }

}