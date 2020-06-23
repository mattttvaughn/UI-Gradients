package io.github.mattpvaughn.uigradients.features.library

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import io.github.mattpvaughn.uigradients.application.MainActivity
import io.github.mattpvaughn.uigradients.data.local.model.Gradient
import io.github.mattpvaughn.uigradients.databinding.FragmentLibraryBinding
import io.github.mattpvaughn.uigradients.navigation.Navigator
import javax.inject.Inject

/** Fragment displaying the list of gradients */
class LibraryFragment : Fragment() {

    companion object {
        fun newInstance() = LibraryFragment()
    }

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var viewModelFactory: LibraryViewModel.Factory

    private val adapter = GradientAdapter(object : ModelClick {
        override fun onClick(gradient: Gradient) {
            navigator.openDetails(gradient)
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
        binding.libraryList.adapter = adapter

        val viewModel = ViewModelProvider(this, viewModelFactory).get(LibraryViewModel::class.java)

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            if (!it.hasBeenHandled) {
                Toast.makeText(requireContext(), it.getContentIfNotHandled(), LENGTH_SHORT).show()
            }
        })

        // Note: if this gets any longer, a [FragmentLibraryBinding.bind()] or databinding would
        // simplify things a lot
        viewModel.viewState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is LibraryViewModel.ViewState.GradientList -> {
                    binding.progress.visibility = View.GONE
                    binding.libraryList.visibility = View.VISIBLE
                    binding.loadingFailedIcon.visibility = View.GONE
                    binding.noGradientsMessage.visibility = View.GONE
                    adapter.submitList(it.gradients)
                }
                is LibraryViewModel.ViewState.Loading -> {
                    binding.progress.visibility = View.VISIBLE
                    binding.libraryList.visibility = View.GONE
                    binding.loadingFailedIcon.visibility = View.GONE
                    binding.noGradientsMessage.visibility = View.GONE
                }
                is LibraryViewModel.ViewState.NoItemsFound -> {
                    binding.progress.visibility = View.GONE
                    binding.libraryList.visibility = View.GONE
                    binding.loadingFailedIcon.visibility = View.GONE
                    binding.noGradientsMessage.visibility = View.VISIBLE
                }
                is LibraryViewModel.ViewState.Error -> {
                    binding.progress.visibility = View.GONE
                    binding.libraryList.visibility = View.GONE
                    binding.loadingFailedIcon.visibility = View.VISIBLE
                    binding.noGradientsMessage.visibility = View.GONE
                }
            }
        })

        binding.loadingFailedIcon.setOnClickListener {
            viewModel.reload()
        }

        (activity as MainActivity).setSupportActionBar(binding.toolbar)

        return binding.root
    }

    interface ModelClick {
        fun onClick(gradient: Gradient)
    }
}
