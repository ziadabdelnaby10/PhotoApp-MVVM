package com.example.photoapp.util

import android.widget.ImageButton
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.photoapp.R
import de.hdodenhof.circleimageview.CircleImageView

@BindingAdapter("imageUrl")
fun ImageView.imageUrl(imageUrl : String){
    if(imageUrl.isNotEmpty())
        Glide.with(context).load(imageUrl).into(this)
}

@BindingAdapter("imageUrlForUser")
fun CircleImageView.imageUrlForUser( imageUrl : String){
    if(imageUrl.isNotEmpty())
        Glide.with(context).load(imageUrl).placeholder(R.drawable.ic_baseline_loading_40).into(this)
}

/*@BindingAdapter("isFavourite")
fun ImageButton.isFavourite(isFav : Boolean){
    if(isFav)
        this.setImageResource(R.drawable.ic_baseline_favorite_24)
    else
        this.setImageResource(R.drawable.ic_baseline_favorite_not_24)
}*/

