package com.harnet.dogbreeds.view

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.harnet.dogbreeds.R
import com.harnet.dogbreeds.databinding.FragmentDetailBinding
import com.harnet.dogbreeds.model.DogPalette
import com.harnet.dogbreeds.util.getProgressDrawable
import com.harnet.dogbreeds.util.loadImage
import com.harnet.dogbreeds.viewModel.DetailViewModel
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment() {
    private lateinit var viewModel: DetailViewModel
    private lateinit var dataBinding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_detail, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)

        // receive arguments from sending fragment
        arguments?.let {
            val dogId = DetailFragmentArgs.fromBundle(it).dogId
            //Retrieve a data from DetailViewModel
            //TODO send a dog id as argument
            viewModel.fetch(dogId)

        }
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.dogLiveData.observe(this, Observer { dog ->
            // if dog isn't null
            dog?.let {
                dataBinding.dogObj = dog

                // set image to Palette
                it.imageURL?.let { url ->
                    setupBackgroundColor(url)
                }
            }
        })
    }

    // Palette handler
    private fun setupBackgroundColor(url: String){
        Glide.with(this)
            .asBitmap()
            .load(url)
            .into(object : CustomTarget<Bitmap>(){
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    Palette.from(resource)
                        .generate {palette->
                            //extract color. If rgb is null intColor = 0
                            val intColor = palette?.vibrantSwatch?.rgb ?: 0
                            //create an object of Palette
                            val articlePalette = DogPalette(intColor)
                            //bind object to View xml
                            dataBinding.palette = articlePalette
                        }
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
    }
}