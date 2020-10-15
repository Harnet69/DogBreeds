package com.harnet.dogbreeds.view

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.harnet.dogbreeds.R
import com.harnet.dogbreeds.databinding.ItemDogBinding
import com.harnet.dogbreeds.model.DogBreed
import com.harnet.dogbreeds.util.OwnImageManager
import com.harnet.dogbreeds.util.getProgressDrawable
import com.harnet.dogbreeds.util.loadImage
import kotlinx.android.synthetic.main.item_dog.view.*
import java.util.concurrent.CompletableFuture

class DogsListAdapter(val dogsList: ArrayList<DogBreed>) :
    RecyclerView.Adapter<DogsListAdapter.DogViewHolder>() {
    //for updating information from a backend
    fun updateDogList(newDogsList: List<DogBreed>) {
        dogsList.clear()
        dogsList.addAll(newDogsList)
        //reset RecycleView and recreate a list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val inflator = LayoutInflater.from(parent.context)
        // elements of the list transformed into views
//        val view = inflator.inflate(R.layout.item_dog, parent, false)
        val view = DataBindingUtil.inflate<ItemDogBinding>(inflator,R.layout.item_dog, parent, false)
        return DogViewHolder(view)
    }

    override fun getItemCount() = dogsList.size

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        // bind the dog object to ImageView from xml file
        holder.view.dog = dogsList[position]
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

//    // load image with own ImageLoader(implemented in Utils for DataBinding usage)
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
}