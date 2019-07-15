package com.seigneur.gauvain.wowsplash.ui.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.seigneur.gauvain.wowsplash.R
import com.seigneur.gauvain.wowsplash.data.model.Photo
import com.seigneur.gauvain.wowsplash.ui.base.BaseFragment
import com.seigneur.gauvain.wowsplash.ui.base.list.NetworkItemCallback
import com.seigneur.gauvain.wowsplash.ui.base.list.NetworkState
import com.seigneur.gauvain.wowsplash.ui.base.list.Status
import com.seigneur.gauvain.wowsplash.ui.home.list.adapter.PhotoItemCallback
import com.seigneur.gauvain.wowsplash.ui.home.list.adapter.PhotoListAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.list_item_network_state.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment(), PhotoItemCallback, NetworkItemCallback {

    private val mHomeViewModel by viewModel<HomeViewModel>()
    private lateinit var mGridLayoutManager:GridLayoutManager

    private val photoListAdapter: PhotoListAdapter by lazy {
        PhotoListAdapter(this, this)
    }

    override val fragmentLayout: Int
        get() = R.layout.fragment_home

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
            photoList.adapter = photoListAdapter
        }

        mHomeViewModel.shotList?.observe(
            viewLifecycleOwner, Observer<PagedList<Photo>> {
                photoListAdapter.submitList(it)

            })

        mHomeViewModel.networkState.observe(viewLifecycleOwner, Observer<NetworkState> {
            photoListAdapter.setNetworkState(it!!)
        })

    }

    override fun onShotClicked(position: Int) {

    }

    /**
     * Init swipe to refresh and enable pull to refresh only when there are items in the adapter
     */
    private fun initSwipeToRefresh() {
        mHomeViewModel.refreshState.observe(this,
            Observer<NetworkState> { networkState ->
                if (networkState != null) {
                    if (photoListAdapter.currentList != null) {
                        if (photoListAdapter.currentList!!.size > 0) {
                            swipeRefreshLayout.isRefreshing = networkState.status == NetworkState.LOADING.status

                            setInitialLoadingState(networkState)
                        } else {
                            setInitialLoadingState(networkState)
                            if (swipeRefreshLayout.isRefreshing) {
                                swipeRefreshLayout.isRefreshing=false
                            }
                        }
                    } else {
                        setInitialLoadingState(networkState)
                        if (swipeRefreshLayout.isRefreshing) {
                            swipeRefreshLayout.isRefreshing=false
                        }
                    }
                }
            })
        swipeRefreshLayout.setOnRefreshListener {
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
        globalNetworkState.visibility = if (networkState.status == Status.SUCCESS) View.GONE else View.VISIBLE
        //set default state
        swipeRefreshLayout.isEnabled = networkState.status == Status.SUCCESS

    }

    override fun retry() {
        mHomeViewModel.retry()
    }


}
