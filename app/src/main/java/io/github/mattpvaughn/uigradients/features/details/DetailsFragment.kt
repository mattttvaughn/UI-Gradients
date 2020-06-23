package io.github.mattpvaughn.uigradients.features.details

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
import io.github.mattpvaughn.uigradients.databinding.FragmentDetailsBinding
import io.github.mattpvaughn.uigradients.util.observeEvent
import javax.inject.Inject

/** The screen showing the data for a given gradient */
class DetailsFragment : Fragment() {

    companion object {
        fun newInstance() = DetailsFragment()
        const val ARG_GRADIENT = "gradient"
    }

    @Inject
    lateinit var factory: DetailsViewModel.Factory

    override fun onAttach(context: Context) {
        (activity as MainActivity).activityComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDetailsBinding.inflate(inflater, container, false)

        val gradient = requireNotNull(arguments?.getParcelable<Gradient>(ARG_GRADIENT))
        factory.gradient = gradient
        val viewModel = ViewModelProvider(this, factory).get(DetailsViewModel::class.java)

        viewModel.messageForUser.observeEvent(viewLifecycleOwner) { message ->
            Toast.makeText(requireContext(), message, LENGTH_SHORT).show()
        }

        viewModel.gradientLiveData.observe(viewLifecycleOwner, Observer { gradientDetails ->
            binding.bind(gradientDetails)
        })

        binding.toolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }

        return binding.root
    }
}
