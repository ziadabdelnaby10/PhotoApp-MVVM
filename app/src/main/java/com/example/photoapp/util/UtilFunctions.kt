package com.example.photoapp.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.widget.Toast
import androidx.lifecycle.MutableLiveData


class UtilFunctions {
    companion object{
        fun hasNetwork(mContext: Context): Boolean? {
            val connectivityManager = mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
            return activeNetwork?.isConnectedOrConnecting == true
        }
        fun showToast(mContext: Context , msg: String){
            Toast.makeText(mContext , msg , Toast.LENGTH_LONG).show()
        }
        fun <T> MutableLiveData<T>.notifyObserver() {
            this.value = this.value
        }
    }

}