package io.github.mattpvaughn.uigradients.application

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.mattpvaughn.uigradients.util.Event
import io.github.mattpvaughn.uigradients.util.postEvent
import javax.inject.Inject


class MainActivityViewModel @Inject constructor() : ViewModel() {

    class Factory @Inject constructor() : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
                return MainActivityViewModel() as T
            }
            throw IllegalArgumentException("Unknown ViewHolder class")
        }
    }

    private var _errorMessage = MutableLiveData<Event<String>>()
    val errorMessage: LiveData<Event<String>>
        get() = _errorMessage

    fun showUserMessage(errorMessage: String) {
        _errorMessage.postEvent(errorMessage)
    }
}

