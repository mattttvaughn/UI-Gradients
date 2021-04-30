package io.github.mattpvaughn.uigradients.injection.modules

import dagger.Module
import dagger.Provides
import io.github.mattpvaughn.uigradients.application.MainActivity
import io.github.mattpvaughn.uigradients.injection.scopes.ActivityScope
import io.github.mattpvaughn.uigradients.navigation.Navigator

@Module
class ActivityModule(private val activity: MainActivity) {

    @Provides
    @ActivityScope
    fun navigator(): Navigator = Navigator(activity.supportFragmentManager)

}


