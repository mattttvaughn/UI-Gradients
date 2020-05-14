package io.github.mattpvaughn.uigradients.features.library

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.github.mattpvaughn.uigradients.data.local.model.Gradient
import io.github.mattpvaughn.uigradients.databinding.GradientListItemBinding


class GradientAdapter(private val modelClick: LibraryFragment.ModelClick) :
    ListAdapter<Gradient, RecyclerView.ViewHolder>(GradientDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding: GradientListItemBinding =
            GradientListItemBinding.inflate(layoutInflater, parent, false)
        return GradientViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as GradientViewHolder).bind(getItem(position), modelClick)
    }

    class GradientViewHolder constructor(private val gradientItemBinding: GradientListItemBinding) :
        RecyclerView.ViewHolder(gradientItemBinding.root) {

        fun bind(gradient: Gradient, modelClick: LibraryFragment.ModelClick) {
            gradientItemBinding.bind(gradient)
            gradientItemBinding.root.setOnClickListener { modelClick.onClick(gradient) }
        }

        companion object {
            fun from(viewGroup: ViewGroup): GradientViewHolder {
                val inflater = LayoutInflater.from(viewGroup.context)
                val binding = GradientListItemBinding.inflate(inflater, viewGroup, false)
                return GradientViewHolder(binding)
            }
        }
    }

}

class GradientDiffCallback : DiffUtil.ItemCallback<Gradient>() {
    override fun areItemsTheSame(oldItem: Gradient, newItem: Gradient): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Gradient, newItem: Gradient): Boolean {
        return oldItem == newItem
    }
}
