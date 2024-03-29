package com.seigneur.gauvain.wowsplash.ui.base.paging.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.seigneur.gauvain.wowsplash.R
import com.seigneur.gauvain.wowsplash.ui.base.BaseFragment
import com.seigneur.gauvain.wowsplash.data.model.network.NetworkState
import com.seigneur.gauvain.wowsplash.data.model.network.Status
import com.seigneur.gauvain.wowsplash.ui.base.paging.NetworkItemCallback
import com.seigneur.gauvain.wowsplash.ui.base.paging.adapter.BasePagedListAdapter
import com.seigneur.gauvain.wowsplash.ui.base.paging.viewModel.BaseSearchResultViewModel
import kotlinx.android.synthetic.main.fragment_search_result.*
import kotlinx.android.synthetic.main.list_item_network_state.*

abstract class BaseSearchPagingFragment<DataSource, Key, Value> :
    BaseFragment(), NetworkItemCallback {

    abstract val listAdapter: BasePagedListAdapter<*, *>
    abstract val vm: BaseSearchResultViewModel<DataSource, Key, Value>
    abstract fun initAdapter()

    override val fragmentLayout: Int
        get() = R.layout.fragment_search_result

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToPagedList() //subscribe in onCreate in case of the livePagedList is already made
        initAdapter()
    }

    abstract fun submitList(list:PagedList<Value>)

    fun performSearch(query:String) {
        vm.search(query)
        setInitialLoadingState(NetworkState.LOADING)
        //subscribe again after perform a search because when we perform a search request we reinitialize LiveData
        //Is not perfect yet, but it works...
        subscribeToPagedList()
    }

    private fun subscribeToPagedList() {
        vm.searchResultList?.observe(
            viewLifecycleOwner, Observer<PagedList<Value>> {
                submitList(it)
            })

        vm.initialNetworkState?.observe(viewLifecycleOwner, Observer {
            setInitialLoadingState(it)
        })

        vm.networkState?.observe(viewLifecycleOwner, Observer<NetworkState> {
            listAdapter.setNetworkState(it)
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
        globalNetworkState.visibility = if (networkState.status == Status.SUCCESS) View.GONE else View.VISIBLE
    }

    override fun retry() {
        vm.retry()
    }


}
