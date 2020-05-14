package io.github.mattpvaughn.uigradients.features.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import io.github.mattpvaughn.uigradients.application.APP_NAME
import io.github.mattpvaughn.uigradients.data.local.model.Gradient
import io.github.mattpvaughn.uigradients.databinding.FragmentDetailsBinding
import io.github.mattpvaughn.uigradients.util.observeEvent

class DetailsFragment : Fragment() {

    companion object {
        fun newInstance() = DetailsFragment()
        const val ARG_GRADIENT = "gradient"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        Log.i(APP_NAME, "DetailsFragment onCreateView()")

        // Activity and context are always non-null on view creation. This informs lint about that
        val context = context!!

        val gradient = requireNotNull(arguments?.getParcelable<Gradient>(ARG_GRADIENT))

        val binding = FragmentDetailsBinding.inflate(inflater, container, false)

        val factory = DetailsViewModelFactory(gradient)
        val viewModel = ViewModelProvider(this, factory).get(DetailsViewModel::class.java)

        viewModel.messageForUser.observeEvent(viewLifecycleOwner) { message ->
            Toast.makeText(context, message, LENGTH_SHORT).show()
        }

        viewModel.gradientLiveData.observe(viewLifecycleOwner, Observer { pokemon ->
            binding.bind(pokemon)
        })

        binding.detailsToolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }

        return binding.root
    }
}
