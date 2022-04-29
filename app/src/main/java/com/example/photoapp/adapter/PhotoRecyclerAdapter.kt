package com.example.photoapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.photoapp.R
import com.example.photoapp.databinding.FavouriteRecyclerviewItemBinding
import com.example.photoapp.databinding.PhotoRecyclerviewItemBinding
import com.example.photoapp.model.Hits
import de.hdodenhof.circleimageview.CircleImageView

class PhotoRecyclerAdapter(private val context: Context
                           , private val onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<PhotoRecyclerAdapter.CustomViewHolder>() {
    var dataSet = mutableListOf<Hits>()

    fun setDataset(dataSet : List<Hits>){
        this.dataSet.clear()
        this.dataSet = dataSet.toMutableList()
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PhotoRecyclerviewItemBinding.inflate(inflater, parent, false)
        return CustomViewHolder(binding , context , onItemClickListener)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount(): Int = dataSet.size

    inner class CustomViewHolder(val binding: PhotoRecyclerviewItemBinding, private val context: Context, private val onItemClickListener: OnItemClickListener) : RecyclerView.ViewHolder(binding.root) {

        init {
            val ani = AnimationUtils.loadAnimation(context , android.R.anim.slide_in_left)
            ani.duration = 2000
            binding.root.startAnimation(ani)
            binding.root.setOnClickListener {
                if(adapterPosition != RecyclerView.NO_POSITION)
                    onItemClickListener.onItemClick(dataSet[adapterPosition])
            }
            binding.root.setOnLongClickListener {
                if(adapterPosition != RecyclerView.NO_POSITION)
                    onItemClickListener.onLongClick(dataSet[adapterPosition])
                true
            }
            binding.favBtn.setOnClickListener {
                if(adapterPosition != RecyclerView.NO_POSITION)
                {
                    onItemClickListener.onFavClick(dataSet[adapterPosition].fav , dataSet[adapterPosition])
                    if(dataSet[adapterPosition].fav)
                        binding.favBtn.setBackgroundResource(R.drawable.ic_baseline_favorite_not_24)
                    else
                        binding.favBtn.setBackgroundResource(R.drawable.ic_baseline_favorite_24)
                }
            }
        }
        //continue
        fun bind(hits : Hits){
            Glide.with(context).load(hits.webformatURL).placeholder(R.drawable.ic_baseline_loading_40).into(binding.imgPhoto)
            Glide.with(context).load(hits.userImageURL).placeholder(R.drawable.ic_baseline_loading_40).into(binding.imgUser)
            binding.txtUserName.text = hits.user
            binding.txtLike.text = hits.likes.toString()
            binding.txtComment.text = hits.comments.toString()
            binding.txtDownload.text = hits.downloads.toString()
            binding.txtView.text = hits.views.toString()
            if(hits.fav)
            {
                binding.favBtn.setBackgroundResource(R.drawable.ic_baseline_favorite_24)
            }
        }
    }

    interface OnItemClickListener{
        fun onItemClick(hit : Hits)
        fun onLongClick(hit : Hits)
        fun onFavClick(isFav :Boolean , hit : Hits)
    }
}