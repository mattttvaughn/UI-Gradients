package io.github.mattpvaughn.uigradients.features.library

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import io.github.mattpvaughn.uigradients.application.APP_NAME
import io.github.mattpvaughn.uigradients.application.MainActivity
import io.github.mattpvaughn.uigradients.data.local.model.Gradient
import io.github.mattpvaughn.uigradients.databinding.FragmentLibraryBinding
import io.github.mattpvaughn.uigradients.navigation.Navigator
import javax.inject.Inject

class LibraryFragment : Fragment() {

    companion object {
        fun newInstance() = LibraryFragment()
    }

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var viewModelFactory: LibraryViewModelFactory

    private val adapter = GradientAdapter(object : ModelClick {
        override fun onClick(gradient: Gradient) {
            openModelDetails(gradient)
        }
    })

    override fun onAttach(context: Context) {
        (activity as MainActivity).activityComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLibraryBinding.inflate(inflater, container, false)

        val viewModel = ViewModelProvider(this, viewModelFactory).get(LibraryViewModel::class.java)

        binding.libraryList.adapter = adapter

        viewModel.gradients.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        (activity as MainActivity).setSupportActionBar(binding.toolbar)

        return binding.root
    }

    private fun openModelDetails(gradient: Gradient) {
        navigator.openDetails(gradient)
    }

    interface ModelClick {
        fun onClick(gradient: Gradient)
    }
}
