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
    var dogId = 0

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
            //Retrieve a data from DetailViewModel by id
            //TODO send a dog id as argument
            viewModel.fetch(dogId.toString())
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
                dogPurposeDetail_TextView.text = dog.temperament
                dogLifespanDetail_TextView.text = dog.lifespan
            }
        })
    }
}