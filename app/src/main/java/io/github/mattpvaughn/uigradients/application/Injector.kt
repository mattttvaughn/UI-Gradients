package io.github.mattpvaughn.uigradients.application

import io.github.mattpvaughn.uigradients.injection.components.AppComponent

class Injector private constructor() {
    companion object {
        fun get() : AppComponent = CustomApplication.get().appComponent
    }
}