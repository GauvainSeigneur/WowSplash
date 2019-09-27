package com.seigneur.gauvain.wowsplash.ui.base.paging.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.seigneur.gauvain.wowsplash.R
import com.seigneur.gauvain.wowsplash.ui.base.BaseFragment
import com.seigneur.gauvain.wowsplash.data.model.network.NetworkState
import com.seigneur.gauvain.wowsplash.data.model.network.Status
import com.seigneur.gauvain.wowsplash.ui.base.paging.NetworkItemCallback
import com.seigneur.gauvain.wowsplash.ui.base.paging.adapter.BasePagedListAdapter
import com.seigneur.gauvain.wowsplash.ui.base.paging.viewModel.BasePagingListViewModel
import kotlinx.android.synthetic.main.layout_refresh_list.*
import kotlinx.android.synthetic.main.list_item_network_state.*
import timber.log.Timber

abstract class BasePagingFragment<DataSource, Key, Value> :
    BaseFragment(), NetworkItemCallback {

    abstract val listAdapter: BasePagedListAdapter<*, *>
    abstract val vm: BasePagingListViewModel<DataSource, Key, Value>
    abstract fun initAdapter()

    override val fragmentLayout: Int
        get() = R.layout.layout_refresh_list

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initSwipeToRefresh()
    }


    /**
     * Init swipe to refresh and enable pull to refresh only when there are items in the adapter
     */
    private fun initSwipeToRefresh() {
        vm.refreshState.observe(this,
            Observer<NetworkState> { networkState ->
                if (networkState != null) {
                    if (listAdapter.currentList != null) {
                        if (listAdapter.currentList!!.size > 0) {
                            photoSwipeRefreshLayout.isRefreshing = networkState.status == NetworkState.LOADING.status
                            setInitialLoadingState(networkState)
                        } else {
                            setInitialLoadingState(networkState)
                            if (photoSwipeRefreshLayout.isRefreshing) {
                                photoSwipeRefreshLayout.isRefreshing = false
                            }
                        }
                    } else {
                        setInitialLoadingState(networkState)
                        if (photoSwipeRefreshLayout.isRefreshing) {
                            photoSwipeRefreshLayout.isRefreshing = false
                        }
                    }
                }
            })
        photoSwipeRefreshLayout.setOnRefreshListener {
            Timber.d("photoSwipeRefreshLayout called")
            vm.refresh()
        }
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
        photoSwipeRefreshLayout.isEnabled = networkState.status == Status.SUCCESS
    }

    override fun retry() {
        vm.retry()
    }


}
