package io.github.mattpvaughn.uigradients.features.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.mattpvaughn.uigradients.data.local.model.Gradient
import io.github.mattpvaughn.uigradients.util.Event
import io.github.mattpvaughn.uigradients.util.postEvent
import javax.inject.Inject

/** Contains the state for the view containing additional information for a gradient */
class DetailsViewModel(gradient: Gradient) : ViewModel() {

    class Factory @Inject constructor() : ViewModelProvider.Factory {
        lateinit var gradient: Gradient

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            check (this::gradient.isInitialized) { "Runtime parameter [gradient] missing" }
            return if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
                DetailsViewModel(gradient) as T
            } else {
                throw IllegalArgumentException("ViewModel Not Found")
            }
        }
    }

    private var _messageForUser = MutableLiveData<Event<String>>()
    val messageForUser: LiveData<Event<String>>
        get() = _messageForUser

    private var _gradientLiveData = MutableLiveData<Gradient>(gradient)
    val gradientLiveData: LiveData<Gradient>
        get() = _gradientLiveData

    private fun showUserMessage(message: String) {
        _messageForUser.postEvent(message)
    }

}
