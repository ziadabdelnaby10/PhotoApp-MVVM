package com.example.photoapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.photoapp.databinding.FavouriteRecyclerviewItemBinding
import com.example.photoapp.model.HitEntity

class FavouriteRecyclerAdapter(
    private val context: Context, private val onItemClickListener: OnItemClickListener
) :
    ListAdapter<HitEntity, FavouriteRecyclerAdapter.CustomViewHolder>(FavouriteDiffCallback()) {
    /*var dataSet = mutableListOf<HitEntity>()

    fun setDataset(dataSet : List<HitEntity>){
        this.dataSet.clear()
        this.dataSet = dataSet.toMutableList()
        notifyDataSetChanged()
    }*/
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = FavouriteRecyclerviewItemBinding.inflate(layoutInflater, parent, false)
        return CustomViewHolder(binding, context, onItemClickListener)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CustomViewHolder(
        val binding: FavouriteRecyclerviewItemBinding,
        context: Context,
        private val onItemClickListener: OnItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            val ani = AnimationUtils.loadAnimation(context, android.R.anim.fade_in)
            ani.duration = 1000
            binding.root.startAnimation(ani)
            binding.root.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION)
                    onItemClickListener.onItemClick(getItem(adapterPosition))
            }
            binding.root.setOnLongClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION)
                    onItemClickListener.onLongClick(getItem(adapterPosition))
                true
            }
        }

        //continue
        fun bind(hits: HitEntity) {
            binding.hit = hits
        }
    }

    interface OnItemClickListener {
        fun onItemClick(hit: HitEntity)
        fun onLongClick(hit: HitEntity)
    }

    class FavouriteDiffCallback : DiffUtil.ItemCallback<HitEntity>() {
        override fun areItemsTheSame(oldItem: HitEntity, newItem: HitEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: HitEntity, newItem: HitEntity): Boolean {
            return oldItem == newItem
        }

    }
}