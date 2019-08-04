package com.seigneur.gauvain.wowsplash.ui.search.photo

import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.search.photo.SearchPhotoDataSource
import com.seigneur.gauvain.wowsplash.data.model.photo.Photo
import com.seigneur.gauvain.wowsplash.ui.base.paging.NetworkItemCallback
import com.seigneur.gauvain.wowsplash.ui.base.paging.adapter.BasePagedListAdapter
import com.seigneur.gauvain.wowsplash.ui.base.paging.fragment.BaseSearchPagingFragment
import com.seigneur.gauvain.wowsplash.ui.base.paging.viewModel.BaseSearchResultViewModel
import com.seigneur.gauvain.wowsplash.ui.list.photo.PhotoItemCallback
import com.seigneur.gauvain.wowsplash.ui.list.photo.PhotoListAdapter
import com.seigneur.gauvain.wowsplash.ui.list.photo.SearchPhotoListAdapter
import com.seigneur.gauvain.wowsplash.ui.search.SearchViewModel
import kotlinx.android.synthetic.main.fragment_search_result.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class SearchPhotoFragment : BaseSearchPagingFragment<SearchPhotoDataSource, Long, Photo>(),
    PhotoItemCallback,
    NetworkItemCallback {

    private val mSearchViewModel by sharedViewModel<SearchViewModel>(from = { parentFragment!! })
    private val mSearchPhotoViewModel by viewModel<SearchPhotoViewModel>()
    //private lateinit var mGridLayoutManager: GridLayoutManager
    private val mSearchPhotoListAdapter: SearchPhotoListAdapter by lazy {
        SearchPhotoListAdapter(this, this)
    }

    override val listAdapter: BasePagedListAdapter<*, *>
        get() = mSearchPhotoListAdapter

    override val vm: BaseSearchResultViewModel<SearchPhotoDataSource, Long, Photo>
        get() = mSearchPhotoViewModel

    override fun submitList(list: PagedList<Photo>) {
        mSearchPhotoListAdapter.submitList(list)
    }

    override fun subscribeToLiveData() {
        mSearchViewModel.searchQuery.observe(viewLifecycleOwner, Observer<Pair<Int, String>> {
            performSearch(it.second)
        })
    }

    override fun initAdapter() {
        if (searchResultList.layoutManager == null && searchResultList.adapter == null) {
            searchResultList.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            searchResultList.adapter = mSearchPhotoListAdapter
        }
    }

    override fun onPhotoClicked(position: Int) {

    }

    override fun retry() {

    }

}
