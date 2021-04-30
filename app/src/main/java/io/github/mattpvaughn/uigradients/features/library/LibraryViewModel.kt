package io.github.mattpvaughn.uigradients.features.library

import androidx.lifecycle.*
import io.github.mattpvaughn.uigradients.data.local.GradientRepository
import io.github.mattpvaughn.uigradients.data.local.IGradientRepo
import io.github.mattpvaughn.uigradients.data.local.model.Gradient
import io.github.mattpvaughn.uigradients.util.CombinedLiveData
import io.github.mattpvaughn.uigradients.util.Event
import io.github.mattpvaughn.uigradients.util.LoadingStatus
import io.github.mattpvaughn.uigradients.util.LoadingStatus.*
import io.github.mattpvaughn.uigradients.util.postEvent
import kotlinx.coroutines.launch
import javax.inject.Inject


/** ViewModel representing the list of all gradients */
class LibraryViewModel(private val repository: IGradientRepo) : ViewModel() {

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(private val gradientRepository: GradientRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(LibraryViewModel::class.java)) {
                LibraryViewModel(gradientRepository) as T
            } else {
                throw IllegalArgumentException("ViewModel Not Found")
            }
        }
    }

    private var gradients = repository.getAllModels()

    /**
     * Show error messages via Toasts. For less transitive errors, something like kotlin-result
     * would be good
     */
    private var _errorMessage = MutableLiveData<Event<String>>()
    val errorMessage: LiveData<Event<String>>
        get() = _errorMessage

    private var loadingState = MutableLiveData<LoadingStatus>(INITIALIZED)

    /** The potential states which the view is restricted to */
    sealed class ViewState {
        object Loading: ViewState()
        object NoItemsFound: ViewState()
        data class GradientList(val gradients: List<Gradient>) : ViewState()
        object Error: ViewState()
    }

    val viewState: CombinedLiveData<List<Gradient>, LoadingStatus, ViewState> =
        CombinedLiveData(gradients, loadingState) { gradientsList, networkState ->
            if (!gradientsList.isNullOrEmpty()) {
                return@CombinedLiveData ViewState.GradientList(gradientsList)
            }
            return@CombinedLiveData when (networkState) {
                LOADING -> ViewState.Loading
                DONE -> ViewState.NoItemsFound
                INITIALIZED, null -> ViewState.NoItemsFound
                ERROR -> ViewState.Error
            }
        }

    fun load() {
        viewModelScope.launch {
            try {
                loadingState.postValue(LOADING)
                // note: updates data only when necessary. The app only refreshes data if 24 hours
                // have passed since the last refresh
                repository.updateData()
                loadingState.postValue(DONE)
            } catch (t: Throwable) {
                _errorMessage.postEvent("Error loading gradients: $t")
                loadingState.postValue(ERROR)
            }
        }
    }
}
