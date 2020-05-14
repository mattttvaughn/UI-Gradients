package io.github.mattpvaughn.uigradients.features.library

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.mattpvaughn.uigradients.data.local.GradientRepository
import io.github.mattpvaughn.uigradients.data.local.model.Gradient
import io.github.mattpvaughn.uigradients.data.remote.UIGradientsApiService
import kotlinx.coroutines.launch


class LibraryViewModel(private val repository: GradientRepository) : ViewModel() {

    private var _gradients = repository.getAllModels()
    val gradients : LiveData<List<Gradient>>
        get() = _gradients

    init {
        viewModelScope.launch {
            repository.updateData()
        }
    }

}
