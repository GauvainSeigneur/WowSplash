package com.seigneur.gauvain.wowsplash.ui.base.paging.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.seigneur.gauvain.wowsplash.R
import com.seigneur.gauvain.wowsplash.data.model.Photo
import com.seigneur.gauvain.wowsplash.ui.base.BaseFragment
import com.seigneur.gauvain.wowsplash.data.model.network.NetworkState
import com.seigneur.gauvain.wowsplash.data.model.network.Status
import com.seigneur.gauvain.wowsplash.ui.base.paging.NetworkItemCallback
import com.seigneur.gauvain.wowsplash.ui.base.paging.adapter.BasePagedListAdapter
import com.seigneur.gauvain.wowsplash.ui.base.paging.viewModel.BasePagingListViewModel
import com.seigneur.gauvain.wowsplash.ui.base.paging.viewModel.BaseSearchResultViewModel
import kotlinx.android.synthetic.main.fragment_refresh_list.*
import kotlinx.android.synthetic.main.list_item_network_state.*
import timber.log.Timber

abstract class BaseSearchPagingFragment<DataSource, Key, Value> :
    BaseFragment(), NetworkItemCallback {

    abstract val listAdapter: BasePagedListAdapter<*, *>
    abstract val vm: BaseSearchResultViewModel<DataSource, Key, Value>
    abstract fun initAdapter()

    override val fragmentLayout: Int
        get() = R.layout.fragment_refresh_list

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
    }

    abstract fun submitList(list:PagedList<Value>)

    fun performSearch(query:String){
        vm.search(query)
        setInitialLoadingState(NetworkState.LOADING)

        /**
         * Subscribe to it only when search is made
         */
        vm.searchResultList?.observe(
            viewLifecycleOwner, Observer<PagedList<Value>> {
                submitList(it)
            })

        vm.networkState.observe(viewLifecycleOwner, Observer<NetworkState> {
            Timber.d("mSearchResultViewModel.networkState $it")
            listAdapter.setNetworkState(it)
            //todo - map two searchResultList & networkState to define if
            setInitialLoadingState(NetworkState.LOADED) // first load is made
        })
    }


    /**
     * Show the current network state for the first load when the user list
     * in the adapter is empty and disable swipe to scroll at the first loading
     *
     * @param networkState the new network state
     */
    private fun setInitialLoadingState(networkState: NetworkState) {
        //error message
        errorMessageTextView.visibility = if (!networkState.message.isEmpty()) View.VISIBLE else View.GONE
        errorMessageTextView.text = networkState.message
        //Visibility according to state
        retryLoadingButton.visibility = if (networkState.status == Status.FAILED) View.VISIBLE else View.GONE
        loadingProgressBar.visibility = if (networkState.status == Status.RUNNING) View.VISIBLE else View.GONE
        photoGlobalNetworkState.visibility = if (networkState.status == Status.SUCCESS) View.GONE else View.VISIBLE
        //set default state
        //photoSwipeRefreshLayout.isEnabled = networkState.status == Status.SUCCESS
    }

    override fun retry() {
        vm.retry()
    }


}
