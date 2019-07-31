package com.seigneur.gauvain.wowsplash.ui.search.collection

import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.search.collection.SearchCollectionDataSource
import com.seigneur.gauvain.wowsplash.data.model.PhotoCollection
import com.seigneur.gauvain.wowsplash.ui.base.paging.NetworkItemCallback
import com.seigneur.gauvain.wowsplash.ui.base.paging.adapter.BasePagedListAdapter
import com.seigneur.gauvain.wowsplash.ui.base.paging.fragment.BaseSearchPagingFragment
import com.seigneur.gauvain.wowsplash.ui.base.paging.viewModel.BaseSearchResultViewModel
import com.seigneur.gauvain.wowsplash.ui.collections.list.adapter.CollectionsItemCallback
import com.seigneur.gauvain.wowsplash.ui.collections.list.adapter.CollectionsListAdapter
import com.seigneur.gauvain.wowsplash.ui.search.SearchViewModel
import kotlinx.android.synthetic.main.fragment_refresh_list.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class SearchCollectionFragment : BaseSearchPagingFragment<SearchCollectionDataSource, Long, PhotoCollection>(), CollectionsItemCallback, NetworkItemCallback {

    private val mSearchViewModel by sharedViewModel<SearchViewModel>(from = { parentFragment!! })
    private val mSearchCollectionViewModel by viewModel<SearchCollectionViewModel>()
    private lateinit var mGridLayoutManager: GridLayoutManager
    private val mCollectionsListAdapter: CollectionsListAdapter by lazy {
        CollectionsListAdapter(this, this)
    }

    override val listAdapter: BasePagedListAdapter<*, *>
        get() = mCollectionsListAdapter

    override val vm: BaseSearchResultViewModel<SearchCollectionDataSource, Long, PhotoCollection>
        get() = mSearchCollectionViewModel

    override fun submitList(list: PagedList<PhotoCollection>) {
        Timber.d("submit list called ")
        mCollectionsListAdapter.submitList(list)
    }

    override fun subscribeToLiveData() {
        mSearchViewModel.searchQuery.observe(viewLifecycleOwner, Observer<Pair<Int, String>> {
            when(it.first) {
                1 ->   performSearch(it.second)
            }
        })
    }

    override fun initAdapter() {
        if (photoList.layoutManager == null && photoList.adapter == null) {
            mGridLayoutManager = GridLayoutManager(context, 1)
            photoList.layoutManager = GridLayoutManager(context, 1)
            photoList.adapter = mCollectionsListAdapter
        }
    }


    override fun onCollectionClicked(position: Int) {

    }

    override fun retry() {

    }

}
