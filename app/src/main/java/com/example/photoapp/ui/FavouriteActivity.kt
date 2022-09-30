package com.example.photoapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.photoapp.R
import com.example.photoapp.adapter.FavouriteRecyclerAdapter
import com.example.photoapp.api.RetrofitClient
import com.example.photoapp.database.HitsDatabase
import com.example.photoapp.databinding.ActivityFavouriteBinding
import com.example.photoapp.model.HitEntity
import com.example.photoapp.repo.ApiRepo
import com.example.photoapp.repo.DatabaseRepo
import com.example.photoapp.util.UtilFunctions
import com.example.photoapp.viewmodel.PhotosActivityViewModel
import com.example.photoapp.viewmodel.PhotosViewModelFactory

class FavouriteActivity : AppCompatActivity(), FavouriteRecyclerAdapter.OnItemClickListener {

    private lateinit var binding: ActivityFavouriteBinding
    private lateinit var viewModel: PhotosActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_favourite)

        val adapter = FavouriteRecyclerAdapter(applicationContext, this)
        val viewModelProvider = PhotosViewModelFactory(
            ApiRepo(RetrofitClient.getInstance(this)!!),
            DatabaseRepo(HitsDatabase.getInstance(applicationContext).HitsDao())
        )
        viewModel = ViewModelProvider(this, viewModelProvider)[PhotosActivityViewModel::class.java]
        binding.mainRv.adapter = adapter

        viewModel.savedPhotosList.observe(this) {
            //continue
            adapter.submitList(it)
        }
        viewModel.errorMsg.observe(this) {
            UtilFunctions.showToast(applicationContext, it)
        }

    }

    override fun onItemClick(hit: HitEntity) {
        val intent = Intent(this, DisplayActivity::class.java)
        intent.putExtra("photo", hit.largeImageURL)
        intent.putExtra("userImg", hit.userImageURL)
        intent.putExtra("userName", hit.user)
        startActivity(intent)
    }

    override fun onLongClick(hit: HitEntity) {
        AlertDialog.Builder(this).setTitle("Delete")
            .setMessage("Are you sure you want to delete this image ?")
            .setPositiveButton(R.string.yes) { dialogInterface, i ->
                viewModel.deletePhotoFromDatabase(hit)
            }.setNegativeButton(R.string.no, null)
            .show()
    }

}