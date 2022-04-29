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
import com.example.photoapp.model.HitEntity
import com.example.photoapp.model.Hits
import de.hdodenhof.circleimageview.CircleImageView

class FavouriteRecyclerAdapter(private val context: Context
                               , private val onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<FavouriteRecyclerAdapter.CustomViewHolder>() {
    var dataSet = mutableListOf<HitEntity>()

    fun setDataset(dataSet : List<HitEntity>){
        this.dataSet.clear()
        this.dataSet = dataSet.toMutableList()
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.favourite_recyclerview_item, parent, false)
        return CustomViewHolder(view , context , onItemClickListener)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount(): Int = dataSet.size

    inner class CustomViewHolder(view: View, private val context: Context, private val onItemClickListener: OnItemClickListener) : RecyclerView.ViewHolder(view) {
        private val userImg : CircleImageView = view.findViewById(R.id.img_user)
        private val userName : TextView = view.findViewById(R.id.txt_user_name)
        private val photo : ImageView = view.findViewById(R.id.img_photo)
        private val like : TextView = view.findViewById(R.id.txt_like)
        private val comment : TextView = view.findViewById(R.id.txt_comment)
        private val download : TextView = view.findViewById(R.id.txt_download)
        private val views : TextView = view.findViewById(R.id.txt_view)

        init {
            val ani = AnimationUtils.loadAnimation(context , android.R.anim.fade_in)
            ani.duration = 1000
            view.startAnimation(ani)
            view.setOnClickListener {
                if(adapterPosition != RecyclerView.NO_POSITION)
                    onItemClickListener.onItemClick(dataSet[adapterPosition])
            }
            view.setOnLongClickListener {
                if(adapterPosition != RecyclerView.NO_POSITION)
                    onItemClickListener.onLongClick(dataSet[adapterPosition])
                true
            }
        }
        //continue
        fun bind(hits : HitEntity){
            Glide.with(context).load(hits.webformatURL).placeholder(R.drawable.ic_baseline_loading_40).into(photo)
            Glide.with(context).load(hits.userImageURL).placeholder(R.drawable.ic_baseline_loading_40).into(userImg)
            userName.text = hits.user
            like.text = hits.likes.toString()
            comment.text = hits.comments.toString()
            download.text = hits.downloads.toString()
            views.text = hits.views.toString()
        }
    }

    interface OnItemClickListener{
        fun onItemClick(hit : HitEntity)
        fun onLongClick(hit : HitEntity)
    }
}