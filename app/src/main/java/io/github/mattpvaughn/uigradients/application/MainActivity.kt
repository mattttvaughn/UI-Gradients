package io.github.mattpvaughn.uigradients.application

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import io.github.mattpvaughn.uigradients.databinding.ActivityMainBinding
import io.github.mattpvaughn.uigradients.injection.components.ActivityComponent
import io.github.mattpvaughn.uigradients.injection.components.DaggerActivityComponent
import io.github.mattpvaughn.uigradients.injection.modules.ActivityModule
import io.github.mattpvaughn.uigradients.injection.scopes.ActivityScope
import io.github.mattpvaughn.uigradients.navigation.Navigator
import io.github.mattpvaughn.uigradients.util.observeEvent
import javax.inject.Inject


@ActivityScope
open class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by lazy {
        ViewModelProvider(this, mainActivityViewModelFactory).get(MainActivityViewModel::class.java)
    }

    @Inject
    lateinit var mainActivityViewModelFactory: MainActivityViewModel.Factory

    @Inject
    lateinit var navigator: Navigator

    lateinit var activityComponent: ActivityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        activityComponent = DaggerActivityComponent.builder()
            .appComponent((application as CustomApplication).appComponent)
            .activityModule(ActivityModule(this))
            .build()
        activityComponent.inject(this)

        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Check if the app is going to be restored, if not, show the library page
        if (savedInstanceState == null) {
            navigator.openLibrary()
        }

        viewModel.errorMessage.observeEvent(this) { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        }
    }
}
