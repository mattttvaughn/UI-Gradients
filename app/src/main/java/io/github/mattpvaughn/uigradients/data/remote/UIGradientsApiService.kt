package io.github.mattpvaughn.uigradients.data.remote

import io.github.mattpvaughn.uigradients.data.local.model.Gradient
import retrofit2.http.*


const val BASE_URL = "https://raw.githubusercontent.com/ghosh/uiGradients/master/"

/** Overkill for fetching a single file but easy... */
interface UIGradientsApiService {
    @GET("https://raw.githubusercontent.com/ghosh/uiGradients/master/gradients.json")
    suspend fun fetchGradientsList(): List<Gradient>
}

