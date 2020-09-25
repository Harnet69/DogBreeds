package com.harnet.dogbreeds.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.harnet.dogbreeds.R
import com.harnet.dogbreeds.viewModel.ListViewModel
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment() {

    private lateinit var viewModel: ListViewModel // viewModel for handling data and views
    val dogListAdapter = DogsListAdapter(arrayListOf()) // handling with RecycleView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //instantiate view model inside the fragment
        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        //initiating observable variables
        viewModel.refresh()

        // RecycleView applying
        dogsList_RecyclerView.apply {
            //allows system to order item elements sequentially in a linear fashion from top to bottom
            layoutManager = LinearLayoutManager(context)
            // attach an adapter
            adapter = dogListAdapter
        }

        // Swiper refresh listener(screen refreshing process)
        refreshLayout.setOnRefreshListener {
            dogsList_RecyclerView.visibility = View.GONE
            listError_TextView.visibility = View.GONE
            loadingView_ProgressBar.visibility = View.VISIBLE
            viewModel.refresh()
            refreshLayout.isRefreshing = false // disappears little spinner on the top
        }

        observeViewModel()
    }

    fun observeViewModel() {
        // update the layout using values of mutable variables from a ViewModel
        viewModel.dogs.observe(this, Observer { dogs ->
            //checking is dogs list isn't null
            dogs?.let {
                dogsList_RecyclerView.visibility = View.VISIBLE
                dogListAdapter.updateDogList(dogs)
            }
        })

        // make error TextViewVisible
        viewModel.dogsLoadError.observe(this, Observer { isError ->
            // check isError not null
            isError?.let {
                //  ternary operator
                listError_TextView.visibility = if (it) View.VISIBLE else View.GONE
            }
        })

        // loading spinner
        viewModel.loading.observe(this, Observer { isLoading ->
            //check isLoading not null
            isLoading?.let {
                // if data still loading - show spinner, else - remove it
                loadingView_ProgressBar.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    //hide all views when progress bar is visible
                    listError_TextView.visibility = View.GONE
                    dogsList_RecyclerView.visibility = View.GONE
                }
            }
        })
    }
}