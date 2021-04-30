package io.github.mattpvaughn.uigradients.data.remote

import io.github.mattpvaughn.uigradients.data.local.model.Gradient
import retrofit2.http.GET


const val BASE_URL = "https://raw.githubusercontent.com/ghosh/uiGradients/master/"

/**
 * Provides access to the UI Gradients endpoint
 *
 * Note: Overkill for fetching a single file but scalable
 */
interface UIGradientsApi {
    @GET("gradients.json")
    suspend fun fetchGradientsList(): List<Gradient>
}

