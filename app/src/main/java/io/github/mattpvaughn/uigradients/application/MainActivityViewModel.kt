package io.github.mattpvaughn.uigradients.application

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.mattpvaughn.uigradients.util.Event
import io.github.mattpvaughn.uigradients.util.postEvent
import javax.inject.Inject


class MainActivityViewModel @Inject constructor() : ViewModel() {

    private var _errorMessage = MutableLiveData<Event<String>>()
    val errorMessage: LiveData<Event<String>>
        get() = _errorMessage

    fun showUserMessage(errorMessage: String) {
        _errorMessage.postEvent(errorMessage)
    }
}

