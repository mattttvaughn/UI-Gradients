package io.github.mattpvaughn.uigradients.util

sealed class NetworkState {
    data class ERROR(val message: String): NetworkState()
    object LOADED: NetworkState()
    object INITIALIZED: NetworkState()
    object LOADING: NetworkState()
}