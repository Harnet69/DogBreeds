package com.harnet.dogbreeds.view

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.harnet.dogbreeds.util.FragmentBindingProvider
import com.harnet.dogbreeds.R
import com.harnet.dogbreeds.databinding.FragmentDetailBinding
import com.harnet.dogbreeds.roomDb.DogPalette
import com.harnet.dogbreeds.viewModel.DogDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {
    // inject viewModel via Hilt
    private val viewModelDog: DogDetailViewModel by viewModels()
    private val dataBinding: FragmentDetailBinding by FragmentBindingProvider(R.layout.fragment_detail)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // receive arguments from sending fragment
        arguments?.let {
            val dogId = DetailFragmentArgs.fromBundle(it).dogId
            //Retrieve a data from DetailViewModel
            viewModelDog.fetch(dogId)

        }
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModelDog.dogLiveData.observe(this, { dog ->
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
                            val intColor = palette?.lightMutedSwatch?.rgb ?: 0
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