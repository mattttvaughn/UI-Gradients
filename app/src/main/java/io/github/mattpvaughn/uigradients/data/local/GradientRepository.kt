package io.github.mattpvaughn.uigradients.data.local

import androidx.lifecycle.LiveData
import io.github.mattpvaughn.uigradients.data.local.PrefsRepo.Companion.REFRESH_FREQUENCY_MINUTES
import io.github.mattpvaughn.uigradients.data.local.model.Gradient
import io.github.mattpvaughn.uigradients.data.remote.UIGradientsApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

interface IGradientRepo {
    suspend fun updateData()
    fun getAllModels(): LiveData<List<Gradient>>
}

/** Repository layer abstracting network/local fetching of gradient data */
@Singleton
class GradientRepository @Inject constructor(
    private val prefsRepo: PrefsRepo,
    private val gradientDao: GradientDao,
    private val uiGradientsApi: UIGradientsApi,
    private val backgroundDispatcher: CoroutineDispatcher = Dispatchers.IO
) : IGradientRepo {

    /**
     * Updates the gradient data in the repo if [REFRESH_FREQUENCY_MINUTES] have passed since the
     * last successful refresh, b/c the source is not updated often
     */
    override suspend fun updateData() {
        val currentEpoch = System.currentTimeMillis()
        if (currentEpoch - prefsRepo.lastRefreshedDataEpoch > REFRESH_FREQUENCY_MINUTES * 60 * 1000) {
            withContext(backgroundDispatcher) {
                val gradients = uiGradientsApi.fetchGradientsList()
                // clear gradients in case there are gradients which have been removed from the
                // network list
                gradientDao.clear()
                gradientDao.insertAll(gradients)
                prefsRepo.lastRefreshedDataEpoch = currentEpoch
            }
        }
    }

    override fun getAllModels(): LiveData<List<Gradient>> {
        return gradientDao.getAllRows()
    }
}