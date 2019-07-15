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
import com.seigneur.gauvain.wowsplash.ui.collections.list.adapter.CollectionsItemCallback
import com.seigneur.gauvain.wowsplash.ui.collections.list.adapter.CollectionsListAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CollectionsFragment : BaseFragment(), CollectionsItemCallback, NetworkItemCallback {

    private val mCollectionsViewModel by viewModel<CollectionsViewModel>()
    private lateinit var mGridLayoutManager:GridLayoutManager

    private val collectionsListAdapter: CollectionsListAdapter by lazy {
        CollectionsListAdapter(this, this)
    }

    override val fragmentLayout: Int
        get() = R.layout.fragment_home

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mCollectionsViewModel.init()
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

        mCollectionsViewModel.shotList?.observe(
            viewLifecycleOwner, Observer<PagedList<PhotoCollection>> {
                collectionsListAdapter.submitList(it)
            })

        mCollectionsViewModel.networkState.observe(viewLifecycleOwner, Observer<NetworkState> {
            collectionsListAdapter.setNetworkState(it!!)
        })

    }

    override fun onCollectionClicked(position: Int) {
    }

    override fun retry() {

    }

    /**
     * Init swipe to refresh and enable pull to refresh only when there are items in the adapter
     */
    private fun initSwipeToRefresh() {

    }

}
