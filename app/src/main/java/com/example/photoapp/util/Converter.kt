package com.example.photoapp.util

import androidx.databinding.InverseMethod

object Converter {
    @InverseMethod("intToString")
    @JvmStatic fun stringToInt(value : String?) : Int{
        return if(value.isNullOrEmpty()) 0 else value.toInt()
    }
    @JvmStatic fun intToString(value : Int?) : String{
        return value.toString()
    }
}