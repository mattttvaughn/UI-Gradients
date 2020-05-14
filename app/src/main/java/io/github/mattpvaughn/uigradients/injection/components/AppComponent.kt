package io.github.mattpvaughn.uigradients.injection.components

import android.content.Context
import android.content.SharedPreferences
import dagger.Component
import io.github.mattpvaughn.uigradients.application.CustomApplication
import io.github.mattpvaughn.uigradients.data.local.*
import io.github.mattpvaughn.uigradients.data.remote.UIGradientsApiService
import io.github.mattpvaughn.uigradients.features.library.LibraryViewModelFactory
import io.github.mattpvaughn.uigradients.injection.modules.AppModule
import java.io.File
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun applicationContext(): Context
    fun internalFilesDir(): File
    fun externalDeviceDirs(): List<File>
    fun sharedPrefs(): SharedPreferences
    fun prefsRepo(): PrefsRepo
    fun gradientRepo(): GradientRepository
    fun remoteService(): UIGradientsApiService
    fun libraryViewModelFactory(): LibraryViewModelFactory

    // Inject
    fun inject(customApplication: CustomApplication)
}