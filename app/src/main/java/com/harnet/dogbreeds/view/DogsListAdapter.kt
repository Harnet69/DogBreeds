package com.harnet.dogbreeds.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.harnet.dogbreeds.R
import com.harnet.dogbreeds.model.DogBreed
import com.harnet.dogbreeds.util.getProgressDrawable
import com.harnet.dogbreeds.util.loadImage
import kotlinx.android.synthetic.main.fragment_detail.view.*
import kotlinx.android.synthetic.main.item_dog.view.*

class DogsListAdapter(val dogsList: ArrayList<DogBreed>) : RecyclerView.Adapter<DogsListAdapter.DogViewHolder>() {

    //for updating information from a backend
    fun updateDogList(newDogsList: List<DogBreed>){
        dogsList.clear()
        dogsList.addAll(newDogsList)
        //reset RecycleView and recreate a list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val inflator = LayoutInflater.from(parent.context)
        // elements of the list transforms into views
        val view = inflator.inflate(R.layout.item_dog, parent, false)
        return DogViewHolder(view)
    }

    override fun getItemCount() = dogsList.size

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        //attach view to information from a list
        // user KTX extended loadImage function(context we can get from any view!!!)
        holder.view.dogImage_ImageView.loadImage(dogsList[position].imageURL, getProgressDrawable(holder.view.context))
        holder.view.dogName_LinearLayout.text = dogsList[position].dogBreed
        holder.view.dogLifespan.text = dogsList[position].lifespan
        //add click listener to item and bind it with detail page
        holder.view.setOnClickListener {
            // navigate to appropriate detail fragment
            //TODO send an appropriate entity of DogBreed class there
            val action = ListFragmentDirections.actionDetailFragment()
            // send dog id to DetailFragment
            action.dogId = position
            Navigation.findNavController(it).navigate(action)
        }
    }

    class DogViewHolder(var view: View) : RecyclerView.ViewHolder(view)
}