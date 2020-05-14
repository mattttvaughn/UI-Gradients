package io.github.mattpvaughn.uigradients.features.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.mattpvaughn.uigradients.data.local.model.Gradient

class DetailsViewModelFactory(private val gradient: Gradient) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            DetailsViewModel(gradient) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }


}
