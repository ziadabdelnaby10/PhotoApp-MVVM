package com.example.photoapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.photoapp.R
import com.example.photoapp.databinding.ActivityDisplayBinding


class DisplayActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityDisplayBinding = DataBindingUtil.setContentView(this , R.layout.activity_display)
        setContentView(binding.root)
        Glide.with(applicationContext).load(intent.getStringExtra("photo")).into(binding.photoDisplay)
        Glide.with(applicationContext).load(intent.getStringExtra("userImg")).into(binding.userImgDisplay)
        binding.userTextDisplay.text = intent.getStringExtra("userName")
    }
}