package io.github.mattpvaughn.uigradients.injection.components

import dagger.Component
import io.github.mattpvaughn.uigradients.application.MainActivity
import io.github.mattpvaughn.uigradients.application.MainActivityViewModel
import io.github.mattpvaughn.uigradients.features.details.DetailsFragment
import io.github.mattpvaughn.uigradients.features.library.LibraryFragment
import io.github.mattpvaughn.uigradients.injection.modules.ActivityModule
import io.github.mattpvaughn.uigradients.injection.scopes.ActivityScope
import io.github.mattpvaughn.uigradients.navigation.Navigator

@ActivityScope
@Component(dependencies = [AppComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {
    fun mainActivityViewModelFactory(): MainActivityViewModel.Factory
    fun navigator(): Navigator

    fun inject(activity: MainActivity)
    fun inject(detailsFragment: DetailsFragment)
    fun inject(libraryFragment: LibraryFragment)
}

