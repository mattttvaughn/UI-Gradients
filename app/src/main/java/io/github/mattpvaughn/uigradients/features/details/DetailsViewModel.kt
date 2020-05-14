package io.github.mattpvaughn.uigradients.features.details

import androidx.lifecycle.*
import io.github.mattpvaughn.uigradients.data.local.model.Gradient
import io.github.mattpvaughn.uigradients.util.Event
import io.github.mattpvaughn.uigradients.util.postEvent

class DetailsViewModel(gradient: Gradient) : ViewModel() {

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
