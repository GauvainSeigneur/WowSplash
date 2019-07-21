package com.seigneur.gauvain.wowsplash.ui.collections

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.seigneur.gauvain.wowsplash.R
import com.seigneur.gauvain.wowsplash.data.model.PhotoCollection
import com.seigneur.gauvain.wowsplash.ui.base.BaseFragment
import com.seigneur.gauvain.wowsplash.ui.base.pagingList.NetworkItemCallback
import com.seigneur.gauvain.wowsplash.data.model.network.NetworkState
import com.seigneur.gauvain.wowsplash.data.model.network.Status
import com.seigneur.gauvain.wowsplash.ui.collections.list.adapter.CollectionsItemCallback
import com.seigneur.gauvain.wowsplash.ui.collections.list.adapter.CollectionsListAdapter

import kotlinx.android.synthetic.main.fragment_refresh_list.*
import kotlinx.android.synthetic.main.list_item_network_state.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CollectionListFragment(val collectionType:String?) : BaseFragment(), CollectionsItemCallback, NetworkItemCallback {

    private val mHomeViewModel by viewModel<CollectionsViewModel>()
    private lateinit var mGridLayoutManager:GridLayoutManager


    private val collectionsListAdapter: CollectionsListAdapter by lazy {
        CollectionsListAdapter(this, this)
    }

    override val fragmentLayout: Int
        get() = R.layout.fragment_refresh_list

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mHomeViewModel.collectionType = collectionType
        mHomeViewModel.init()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initSwipeToRefresh()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    private fun initAdapter() {
        if (photoList.layoutManager==null && photoList.adapter==null) {
            mGridLayoutManager = GridLayoutManager(context, 1)
            photoList.layoutManager =  GridLayoutManager(context, 1)
            photoList.adapter = collectionsListAdapter
        }

        mHomeViewModel.list?.observe(
            viewLifecycleOwner, Observer<PagedList<PhotoCollection>> {
                collectionsListAdapter.submitList(it)
            })

        mHomeViewModel.networkState.observe(viewLifecycleOwner, Observer<NetworkState> {
            collectionsListAdapter.setNetworkState(it!!)
        })

    }

    override fun onCollectionClicked(position: Int) {

    }

    /**
     * Init swipe to refresh and enable pull to refresh only when there are items in the adapter
     */
    private fun initSwipeToRefresh() {
        mHomeViewModel.refreshState.observe(this,
            Observer<NetworkState> { networkState ->
                if (networkState != null) {
                    if (collectionsListAdapter.currentList != null) {
                        if (collectionsListAdapter.currentList!!.size > 0) {
                            photoSwipeRefreshLayout.isRefreshing = networkState.status == NetworkState.LOADING.status

                            setInitialLoadingState(networkState)
                        } else {
                            setInitialLoadingState(networkState)
                            if (photoSwipeRefreshLayout.isRefreshing) {
                                photoSwipeRefreshLayout.isRefreshing=false
                            }
                        }
                    } else {
                        setInitialLoadingState(networkState)
                        if (photoSwipeRefreshLayout.isRefreshing) {
                            photoSwipeRefreshLayout.isRefreshing=false
                        }
                    }
                }
            })
        photoSwipeRefreshLayout.setOnRefreshListener {
            mHomeViewModel.refresh()
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
        mHomeViewModel.retry()
    }


}
