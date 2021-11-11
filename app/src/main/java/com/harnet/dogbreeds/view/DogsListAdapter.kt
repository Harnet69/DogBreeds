package com.harnet.dogbreeds.view

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.harnet.dogbreeds.R
import com.harnet.dogbreeds.databinding.ItemDogBinding
import com.harnet.dogbreeds.model.DogBreed
import com.harnet.dogbreeds.model.DogPalette
import javax.inject.Inject

class DogsListAdapter @Inject constructor() :
    RecyclerView.Adapter<DogsListAdapter.DogViewHolder>() {
    private val dogsList: ArrayList<DogBreed> = arrayListOf()

    //for updating information from a backend
    fun updateDogList(newDogsList: List<DogBreed>) {
        dogsList.clear()
        dogsList.addAll(newDogsList)
        //reset RecycleView and recreate a list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        // elements of the list transformed into views
        val view =
            DataBindingUtil.inflate<ItemDogBinding>(inflater, R.layout.item_dog, parent, false)
        return DogViewHolder(view)
    }

    override fun getItemCount() = dogsList.size

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        // bind the dog object to ImageView from xml file
        holder.view.dog = dogsList[position]
        // set image to Palette
        dogsList[position].imageURL?.let { url ->
            setupBackgroundColor(holder, url)
        }
//        holder.view.dogImage_ImageView.setImageResource(R.mipmap.ic_dog_ico)
//        //attach view to information from a list
//        //TODO make switcher for two approaches of images loading: own and Glide's
//
//        // load images by own ImageController (non-Glide approach)
////        loadImageByOwnImageLoader(holder, position)
//
//        // load by user KTX extended loadImage function(context we can get from any view!!!)
//        holder.view.dogImage_ImageView.loadImage(
//            dogsList[position].imageURL,
//            getProgressDrawable(holder.view.context)
//        )
//        // bind data to view elements
//        holder.view.dogName_LinearLayout.text = dogsList[position].dogBreed
//        holder.view.dogLifespan.text = dogsList[position].lifespan
//
//        //add click listener to item and bind it with detail page
//        holder.view.setOnClickListener {
//            // navigate to appropriate detail fragment
//            val action = ListFragmentDirections.actionDetailFragment()
//            // send dog id to DetailFragment
//            action.dogId = dogsList[position].breedId.toString()
//
//            Navigation.findNavController(it).navigate(action)
//        }
    }

    class DogViewHolder(var view: ItemDogBinding) : RecyclerView.ViewHolder(view.root)

    // load image with own ImageLoader(implemented in Utils for DataBinding usage)
//    private fun loadImageByOwnImageLoader(holder: DogViewHolder, position: Int) {
//        holder.view.dogImage_ImageView.drawable.let {
//            val imageController = OwnImageManager()
//            CompletableFuture.supplyAsync {
//                imageController.getImageByLink(dogsList[position].imageURL)
//            }
//                .thenAccept { image ->
//                    val activity = holder.view.context as Activity
//                    activity.runOnUiThread(Runnable {
//                        holder.view.dogImage_ImageView.setImageBitmap(image)
//                    })
//                }
//        }
//    }

    //Fix blinking RecyclerView
    override fun getItemId(position: Int): Long {
        return dogsList[position].uuid.toLong()
    }

    // Palette handler
    private fun setupBackgroundColor(holder: DogViewHolder, url: String) {
        Glide.with(holder.view.dogLifespan.context)
            .asBitmap()
            .load(url)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    Palette.from(resource)
                        .generate { palette ->
                            //extract color. If rgb is null intColor = 0
                            val intColor = palette?.lightMutedSwatch?.rgb ?: 0
                            //create an object of Palette
                            val articlePalette = DogPalette(intColor)
                            //bind object to View xml
                            holder.view.palette = articlePalette
                        }
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
    }
}