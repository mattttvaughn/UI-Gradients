package io.github.mattpvaughn.uigradients.data.remote

import io.github.mattpvaughn.uigradients.data.local.model.Gradient
import retrofit2.http.GET


const val BASE_URL = "https://raw.githubusercontent.com/ghosh/uiGradients/master/"

/** Mega-overkill for fetching a single file ofc but scalable */
interface UIGradientsApiService {
    @GET("gradients.json")
    suspend fun fetchGradientsList(): List<Gradient>
}

