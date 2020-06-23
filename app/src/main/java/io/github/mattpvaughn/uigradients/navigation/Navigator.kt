package io.github.mattpvaughn.uigradients.navigation

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN
import io.github.mattpvaughn.uigradients.R
import io.github.mattpvaughn.uigradients.data.local.model.Gradient
import io.github.mattpvaughn.uigradients.features.details.DetailsFragment
import io.github.mattpvaughn.uigradients.features.details.DetailsFragment.Companion.ARG_GRADIENT
import io.github.mattpvaughn.uigradients.features.library.LibraryFragment

/**
 * Responsible for handling the app's navigation
 *
 * Basically just provides typed methods to start fragments. Eliminates chance of runtime arg errors
 *
 * Long-term note: conversion to use Navigation Component would be a good replacement for this.
 * This will not scale very well.
 */
class Navigator(private val fragmentManager: FragmentManager) {

    private val libraryFragment by lazy {
        LibraryFragment.newInstance()
    }

    fun openLibrary() {
        fragmentManager.beginTransaction().replace(R.id.fragNavHost, libraryFragment).commit()
    }

    fun openDetails(gradient: Gradient) {
        fragmentManager.beginTransaction()
            .setTransition(TRANSIT_FRAGMENT_OPEN)
            .replace(R.id.fragNavHost, DetailsFragment.newInstance().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_GRADIENT, gradient)
                }
            }).addToBackStack(null).commit()
    }

    /**
     * Will be necessary to implement this if app complexity exceeds the default behavior
     * provided by [FragmentManager] (e.g. for something like conditional navigation on back press)
     */
//    fun onBackPressed() {
//        fragmentManager.popBackStack()
//    }
}