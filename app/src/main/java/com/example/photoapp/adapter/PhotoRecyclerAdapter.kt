package com.example.photoapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.photoapp.databinding.PhotoRecyclerviewItemBinding
import com.example.photoapp.model.Hits

class PhotoRecyclerAdapter(
    private val context: Context, private val onItemClickListener: OnItemClickListener
) :
    ListAdapter<Hits, PhotoRecyclerAdapter.CustomViewHolder>(PhotoRecyclerDiffCallBack()) {
    //var dataSet = mutableListOf<Hits>()

    /*fun setDataset(dataSet: List<Hits>) {
        this.dataSet.clear()
        this.dataSet = dataSet.toMutableList()
        notifyDataSetChanged()
    }
*/
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PhotoRecyclerviewItemBinding.inflate(inflater, parent, false)
        return CustomViewHolder(binding, context, onItemClickListener)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class CustomViewHolder(
        private val binding: PhotoRecyclerviewItemBinding,
        context: Context,
        private val onItemClickListener: OnItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            val ani = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left)
            ani.duration = 1000
            binding.root.startAnimation(ani)
            binding.root.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION)
                    onItemClickListener.onItemClick(binding.hit!!)
            }
            binding.root.setOnLongClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION)
                    onItemClickListener.onLongClick(binding.hit!!)
                true
            }
            binding.setOnFavChange { compoundButton, b ->
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onItemClickListener.onFavClick(
                        binding.hit!!.fav,
                        binding.hit!!
                    )
                    /*if (binding.hit.fav)
                        binding.favBtn.setBackgroundResource(R.drawable.ic_baseline_favorite_not_24)
                    else
                        binding.favBtn.setBackgroundResource(R.drawable.ic_baseline_favorite_24)*/
                }
            }
        }

        fun bind(hits: Hits) {
            binding.hit = hits
            binding.executePendingBindings()
        }
    }

    interface OnItemClickListener {
        fun onItemClick(hit: Hits)
        fun onLongClick(hit: Hits)
        fun onFavClick(isFav: Boolean, hit: Hits)
    }

    class PhotoRecyclerDiffCallBack : DiffUtil.ItemCallback<Hits>() {
        override fun areItemsTheSame(oldItem: Hits, newItem: Hits): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Hits, newItem: Hits): Boolean {
            return oldItem == newItem
        }

    }
}