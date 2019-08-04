package com.seigneur.gauvain.wowsplash.ui.collections

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.seigneur.gauvain.wowsplash.R
import com.seigneur.gauvain.wowsplash.data.model.photo.PhotoCollection
import com.seigneur.gauvain.wowsplash.ui.base.BaseFragment
import com.seigneur.gauvain.wowsplash.ui.base.paging.NetworkItemCallback
import com.seigneur.gauvain.wowsplash.data.model.network.NetworkState
import com.seigneur.gauvain.wowsplash.ui.list.collection.CollectionsItemCallback
import com.seigneur.gauvain.wowsplash.ui.list.collection.CollectionsListAdapter

import kotlinx.android.synthetic.main.fragment_refresh_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CollectionListFragment : BaseFragment(),
    CollectionsItemCallback, NetworkItemCallback {

    companion object {
        private val LIST_ARG = "collection_list_arg"
        fun newInstance(collectionListType: Int): CollectionListFragment {
            val args: Bundle = Bundle()
            args.putSerializable(LIST_ARG, collectionListType)
            val fragment = CollectionListFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private val mHomeViewModel by viewModel<CollectionsViewModel>()
    private lateinit var mGridLayoutManager:GridLayoutManager


    private val collectionsListAdapter: CollectionsListAdapter by lazy {
        CollectionsListAdapter(this, this)
    }

    override val fragmentLayout: Int
        get() = R.layout.fragment_refresh_list

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = this.arguments
        if (bundle != null) {
            val type = bundle.getInt(LIST_ARG)
            mHomeViewModel.init(type)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun subscribeToLiveData() {
        mHomeViewModel.list?.observe(
            viewLifecycleOwner, Observer<PagedList<PhotoCollection>> {
                collectionsListAdapter.submitList(it)

            })

        mHomeViewModel.networkState.observe(viewLifecycleOwner, Observer<NetworkState> {
            collectionsListAdapter.setNetworkState(it!!)
        })
    }

    private fun initAdapter() {
        if (photoList.layoutManager==null && photoList.adapter==null) {
            mGridLayoutManager = GridLayoutManager(context, 1)
            photoList.layoutManager =  GridLayoutManager(context, 1)
            photoList.adapter = collectionsListAdapter
        }

    }

    override fun onCollectionClicked(position: Int) {

    }





    override fun retry() {
    }


}
