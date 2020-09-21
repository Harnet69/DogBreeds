package com.harnet.dogbreeds.util

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.harnet.dogbreeds.R

//little loading spinner for image
fun getProgressDrawable(context: Context): CircularProgressDrawable{
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}
//extension to auto loading image for ImageView element
fun ImageView.loadImage(uri: String?, progressDrawable: CircularProgressDrawable){
    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.mipmap.ic_dog_ico)
    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(uri)
        .into(this)// this - extended ImageView class
}