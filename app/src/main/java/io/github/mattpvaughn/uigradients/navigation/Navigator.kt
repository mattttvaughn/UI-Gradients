package io.github.mattpvaughn.uigradients.navigation

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN
import io.github.mattpvaughn.uigradients.features.details.DetailsFragment
import io.github.mattpvaughn.uigradients.features.library.LibraryFragment
import io.github.mattpvaughn.uigradients.R
import io.github.mattpvaughn.uigradients.data.local.model.Gradient
import io.github.mattpvaughn.uigradients.features.details.DetailsFragment.Companion.ARG_GRADIENT

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

    fun onBackPressed() {
        fragmentManager.popBackStack()
    }

}