package io.github.mattpvaughn.uigradients.features.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.mattpvaughn.uigradients.data.local.GradientRepository
import io.github.mattpvaughn.uigradients.data.remote.UIGradientsApiService
import javax.inject.Inject

class LibraryViewModelFactory @Inject constructor(private val gradientRepository: GradientRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(LibraryViewModel::class.java)) {
            LibraryViewModel(gradientRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}
