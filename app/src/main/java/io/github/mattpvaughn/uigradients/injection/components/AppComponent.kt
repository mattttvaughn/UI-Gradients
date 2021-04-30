package io.github.mattpvaughn.uigradients.injection.components

import android.content.Context
import dagger.Component
import io.github.mattpvaughn.uigradients.application.CustomApplication
import io.github.mattpvaughn.uigradients.data.local.GradientRepository
import io.github.mattpvaughn.uigradients.data.remote.UIGradientsApi
import io.github.mattpvaughn.uigradients.features.details.DetailsViewModel
import io.github.mattpvaughn.uigradients.features.library.LibraryViewModel
import io.github.mattpvaughn.uigradients.injection.modules.AppModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun applicationContext(): Context
    fun gradientRepo(): GradientRepository
    fun remoteService(): UIGradientsApi

    // These should each be scoped to their associated fragments, for a number of reasons:
    //  - ability to provide multiple instances
    //  - typesafe runtime injection
    //  - aesthetics/separation of concerns?
    fun detailsViewModelFactory(): DetailsViewModel.Factory
    fun libraryViewModelFactory(): LibraryViewModel.Factory

    fun inject(customApplication: CustomApplication)
}