package com.example.photoapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.photoapp.R
import com.example.photoapp.adapter.PhotoRecyclerAdapter
import com.example.photoapp.api.RetrofitClient
import com.example.photoapp.database.HitsDatabase
import com.example.photoapp.databinding.ActivityMainBinding
import com.example.photoapp.model.HitEntity
import com.example.photoapp.model.Hits
import com.example.photoapp.repo.ApiRepo
import com.example.photoapp.repo.DatabaseRepo
import com.example.photoapp.util.UtilFunctions
import com.example.photoapp.viewmodel.PhotosActivityViewModel
import com.example.photoapp.viewmodel.PhotosViewModelFactory

class PhotosActivity : AppCompatActivity(), PhotoRecyclerAdapter.OnItemClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: PhotosActivityViewModel

    //    private lateinit var dialog : Dialog
//    private lateinit var imageView : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = PhotoRecyclerAdapter(applicationContext, this)
        val viewModelProvider = PhotosViewModelFactory(
            ApiRepo(RetrofitClient.getInstance(this)!!),
            DatabaseRepo(HitsDatabase.getInstance(applicationContext).HitsDao())
        )
        viewModel = ViewModelProvider(this, viewModelProvider)[PhotosActivityViewModel::class.java]
        binding.mainRv.adapter = adapter

        viewModel.photosList.observe(this) {
            adapter.submitList(it.hits)
        }
        viewModel.errorMsg.observe(this) {
            UtilFunctions.showToast(this@PhotosActivity, it)
        }

//        dialog = Dialog(this)
//        dialog.setContentView(R.layout.layout_popup)
//        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        imageView = dialog.findViewById(R.id.img_popup)
//        imageView.setImageResource(R.drawable.ic_launcher_foreground)
//        imageView.setOnClickListener {
//            dialog.dismiss()
//        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.photo_activity_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.fav_activity -> {
                startActivity(Intent(applicationContext, FavouriteActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onItemClick(hit: Hits) {
        val intent = Intent(this, DisplayActivity::class.java)
        intent.putExtra("photo", hit.largeImageURL)
        intent.putExtra("userImg", hit.userImageURL)
        intent.putExtra("userName", hit.user)
        startActivity(intent)
    }

    override fun onLongClick(hit: Hits) {
//        val view = layoutInflater.inflate(R.layout.layout_popup , null)
//        imageView = view.findViewById(R.id.img_popup)
//        Glide.with(applicationContext).load(hit.webformatURL).into(imageView)
//        val toast = Toast(applicationContext)
//        toast.view = view
//        toast.duration = Toast.LENGTH_LONG
//        toast.setGravity(Gravity.CENTER , 0 , 0)
//        toast.show()
//        //Glide.with(applicationContext).load(hit.webformatURL).into(imageView)
//        //dialog.show()
    }

    override fun onFavClick(isFav: Boolean, hit: Hits) {
        if (isFav) {
            //delete
            val data = HitEntity(
                hit.id,
                hit.webformatURL,
                hit.largeImageURL,
                hit.views,
                hit.downloads,
                hit.likes,
                hit.comments,
                hit.userId,
                hit.user,
                hit.userImageURL
            )
            viewModel.deletePhotoFromDatabase(data)
        } else {
            //add
            val data = HitEntity(
                hit.id,
                hit.webformatURL,
                hit.largeImageURL,
                hit.views,
                hit.downloads,
                hit.likes,
                hit.comments,
                hit.userId,
                hit.user,
                hit.userImageURL
            )
            viewModel.addPhotoToDatabase(data)
        }
    }

    override fun onResume() {
        viewModel.getAllPhotosFromAPI()
        super.onResume()
    }
}