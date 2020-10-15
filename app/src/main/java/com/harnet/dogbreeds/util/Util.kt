package com.harnet.dogbreeds.util

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.harnet.dogbreeds.R
import com.harnet.dogbreeds.view.DogsListAdapter
import java.util.concurrent.CompletableFuture

//little loading spinner for image loading
fun getProgressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}

//extension for auto loading image of ImageView element using Glide library
fun ImageView.loadImage(uri: String?, progressDrawable: CircularProgressDrawable) {
    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.mipmap.ic_dog_ico)
    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(uri)
        .into(this)// this - extended ImageView class
}

// load images with Glide library
@BindingAdapter("android:bindImageUrl")
fun loadBindingImage(view: ImageView, url: String?){
    view.loadImage(url, getProgressDrawable(view.context))
}

//// load images with own ImageLoader
@BindingAdapter("android:bindImageUrlByOwnParser")
fun loadImageByOwnImageLoader(view: ImageView, url: String?) {
    view.let {
        val imageController = OwnImageManager()
        CompletableFuture.supplyAsync {
            imageController.getImageByLink(url)
        }
            .thenAccept { image ->
                val activity = view.context as Activity
                activity.runOnUiThread(Runnable {
                    view.setImageBitmap(image)
                })
            }
    }
}