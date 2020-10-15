package com.harnet.dogbreeds.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.harnet.dogbreeds.R
import com.harnet.dogbreeds.databinding.FragmentDetailBinding
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
            }
        })
    }
}