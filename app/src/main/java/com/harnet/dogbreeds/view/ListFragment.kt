package com.harnet.dogbreeds.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.harnet.dogbreeds.util.FragmentBindingProvider
import com.harnet.dogbreeds.R
import com.harnet.dogbreeds.databinding.FragmentListBinding
import com.harnet.dogbreeds.util.SharedPreferencesHelper
import com.harnet.dogbreeds.viewModel.DogsListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ListFragment : Fragment() {
    // inject viewModel via Hilt
    private val viewModelDogs: DogsListViewModel by viewModels()
    private val dataBinding: FragmentListBinding by FragmentBindingProvider(R.layout.fragment_list)

    @Inject
    lateinit var dogListAdapter: DogsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // handle with cache
        if (context?.let { SharedPreferencesHelper.invoke(it).getLastUpdateTime()?.equals(0L) }!!) {
            //initiating observable variables
            viewModelDogs.refreshFromAPI(requireContext())
        } else {
            viewModelDogs.refreshFromDatabase()
        }

        // RecycleView applying
        dataBinding.dogsListRecyclerView.apply {
            //allows system to order item elements sequentially in a linear fashion from top to bottom
            layoutManager = LinearLayoutManager(context)
            //Fix blinking RecyclerView
            adapter?.let {
                if (!it.hasObservers()) {
                    it.setHasStableIds(true)
                }
            }
            // attach an adapter
            adapter = dogListAdapter
        }

        // Swiper refresh listener(screen refreshing process)
        dataBinding.refreshLayout.setOnRefreshListener {
            dataBinding.dogsListRecyclerView.visibility = View.GONE
            dataBinding.listErrorTextView.visibility = View.GONE
            dataBinding.loadingViewProgressBar.visibility = View.VISIBLE
            viewModelDogs.refreshFromAPI(requireContext())
            dataBinding.refreshLayout.isRefreshing = false // disappears little spinner on the top
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        // update the layout using values of mutable variables from a ViewModel
        viewModelDogs.mDogs.observe(this, { dogs ->
            //checking is dogs list isn't null
            dogs?.let {
                dataBinding.dogsListRecyclerView.visibility = View.VISIBLE
                dogListAdapter.updateDogList(dogs)
            }
        })

        // make error TextViewVisible
        viewModelDogs.mDogsLoadError.observe(this, { isError ->
            // check isError not null
            isError?.let {
                //  ternary operator
                dataBinding.listErrorTextView.visibility = if (it) View.VISIBLE else View.GONE
            }
        })

        // loading spinner
        viewModelDogs.mLoading.observe(this, { isLoading ->
            //check isLoading not null
            isLoading?.let {
                // if data still loading - show spinner, else - remove it
                dataBinding.loadingViewProgressBar.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    //hide all views when progress bar is visible
                    dataBinding.listErrorTextView.visibility = View.GONE
                    dataBinding.dogsListRecyclerView.visibility = View.GONE
                }
            }
        })
    }
}