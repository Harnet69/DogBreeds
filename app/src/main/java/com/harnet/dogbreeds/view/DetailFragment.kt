package com.harnet.dogbreeds.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.harnet.dogbreeds.R
import com.harnet.dogbreeds.viewModel.DetailViewModel
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_list.*

class DetailFragment : Fragment() {
    private lateinit var viewModel: DetailViewModel
    lateinit var dogId: String
    lateinit var dogName: String
    lateinit var dogLifeSpan: String
    lateinit var dogBreedGroup: String
    lateinit var dogBredFor: String
    lateinit var dogTemperament: String
    lateinit var dogImagURL: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)

        // receive arguments from sending fragment
        arguments?.let {
            dogId = DetailFragmentArgs.fromBundle(it).dogId
            dogName = DetailFragmentArgs.fromBundle(it).dogName
            dogLifeSpan = DetailFragmentArgs.fromBundle(it).dogLifeSpan
            dogBreedGroup = DetailFragmentArgs.fromBundle(it).dogBreedGroup
            dogBredFor = DetailFragmentArgs.fromBundle(it).dogBredFor
            dogTemperament = DetailFragmentArgs.fromBundle(it).dogTemperament
            dogImagURL = DetailFragmentArgs.fromBundle(it).dogImagURL
            //Retrieve a data from DetailViewModel
            //TODO send a dog id as argument
            viewModel.fetch(dogId, dogName, dogLifeSpan, dogBreedGroup, dogBredFor, dogTemperament, dogImagURL)

        }
        observeViewModel()
    }

    private fun observeViewModel(){
        viewModel.dogLiveData.observe(this, Observer {dog ->
            // if dog isn't null
            dog?.let {
                dogIdDetail_TextView.text = dog.breedId
                dogNameDetail_TextView.text = dog.dogBreed
                dogPurposeDetail_TextView.text = dog.bredFor
                dogTemperamentDetail_TextView.text = dog.temperament
                dogLifespanDetail_TextView.text = dog.lifespan
            }
        })
    }
}