package com.example.photoapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.photoapp.R
import com.example.photoapp.databinding.ActivityDisplayBinding

class DisplayActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDisplayBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDisplayBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Glide.with(applicationContext).load(intent.getStringExtra("photo")).into(binding.photoDisplay)
        Glide.with(applicationContext).load(intent.getStringExtra("userImg")).into(binding.userImgDisplay)
        binding.userTextDisplay.text = intent.getStringExtra("userName")
    }
}