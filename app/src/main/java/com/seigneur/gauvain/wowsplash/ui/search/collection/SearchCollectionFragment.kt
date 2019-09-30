package com.seigneur.gauvain.wowsplash.ui.search.collection

import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.search.collection.SearchCollectionDataSource
import com.seigneur.gauvain.wowsplash.data.model.photo.PhotoCollection
import com.seigneur.gauvain.wowsplash.ui.base.paging.NetworkItemCallback
import com.seigneur.gauvain.wowsplash.ui.base.paging.adapter.BasePagedListAdapter
import com.seigneur.gauvain.wowsplash.ui.base.paging.fragment.BaseSearchPagingFragment
import com.seigneur.gauvain.wowsplash.ui.base.paging.viewModel.BaseSearchResultViewModel
import com.seigneur.gauvain.wowsplash.ui.list.collection.CollectionsItemCallback
import com.seigneur.gauvain.wowsplash.ui.list.collection.CollectionsListAdapter
import com.seigneur.gauvain.wowsplash.ui.search.SearchViewModel
import com.seigneur.gauvain.wowsplash.utils.event.EventObserver
import kotlinx.android.synthetic.main.fragment_search_result.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class SearchCollectionFragment : BaseSearchPagingFragment<SearchCollectionDataSource, Long, PhotoCollection>(),
    CollectionsItemCallback, NetworkItemCallback {

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
        mCollectionsListAdapter.submitList(list)
    }

    override fun subscribeToLiveData() {
        mSearchViewModel.searchCollectionQuery.observe(viewLifecycleOwner, EventObserver<String> {
            performSearch(it)
        })
    }

    override fun initAdapter() {
        if (searchResultList.layoutManager == null && searchResultList.adapter == null) {
            mGridLayoutManager = GridLayoutManager(context, 1)
            searchResultList.layoutManager = GridLayoutManager(context, 1)
            searchResultList.adapter = mCollectionsListAdapter
        }
    }


    override fun onCollectionClicked(position: Int) {

    }

    override fun retry() {

    }

}
