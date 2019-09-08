package com.seigneur.gauvain.wowsplash.ui.collections

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.seigneur.gauvain.wowsplash.R
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.collection.CollectionDataSource
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.photo.PhotosDataSource
import com.seigneur.gauvain.wowsplash.data.model.photo.PhotoCollection
import com.seigneur.gauvain.wowsplash.ui.base.BaseFragment
import com.seigneur.gauvain.wowsplash.ui.base.paging.NetworkItemCallback
import com.seigneur.gauvain.wowsplash.data.model.network.NetworkState
import com.seigneur.gauvain.wowsplash.data.model.photo.Photo
import com.seigneur.gauvain.wowsplash.ui.base.paging.adapter.BasePagedListAdapter
import com.seigneur.gauvain.wowsplash.ui.base.paging.fragment.BasePagingFragment
import com.seigneur.gauvain.wowsplash.ui.base.paging.viewModel.BasePagingListViewModel
import com.seigneur.gauvain.wowsplash.ui.list.collection.CollectionsItemCallback
import com.seigneur.gauvain.wowsplash.ui.list.collection.CollectionsListAdapter

import kotlinx.android.synthetic.main.layout_refresh_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CollectionListFragment : BasePagingFragment<CollectionDataSource, Long, PhotoCollection>(),
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

    private val mCollectionsViewModel by viewModel<CollectionsViewModel>()
    private lateinit var mGridLayoutManager: GridLayoutManager
    private val collectionsListAdapter: CollectionsListAdapter by lazy {
        CollectionsListAdapter(this, this)
    }


    override val fragmentLayout: Int
        get() = R.layout.layout_refresh_list

    override val listAdapter: BasePagedListAdapter<*, *>
        get() = collectionsListAdapter

    override val vm: BasePagingListViewModel<CollectionDataSource, Long, PhotoCollection>
        get() = mCollectionsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = this.arguments
        if (bundle != null) {
            val type = bundle.getInt(LIST_ARG)
            mCollectionsViewModel.init(type)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
    }

    override fun subscribeToLiveData() {
        mCollectionsViewModel.list?.observe(
            viewLifecycleOwner, Observer<PagedList<PhotoCollection>> {
                collectionsListAdapter.submitList(it)

            })

        mCollectionsViewModel.networkState.observe(viewLifecycleOwner, Observer<NetworkState> {
            collectionsListAdapter.setNetworkState(it!!)
        })
    }

    override fun initAdapter() {
        photoList.layoutManager = GridLayoutManager(context, 1)
        photoList.adapter = collectionsListAdapter
    }

    override fun onCollectionClicked(position: Int) {

    }

    override fun retry() {
        mCollectionsViewModel.retry()
    }


}
