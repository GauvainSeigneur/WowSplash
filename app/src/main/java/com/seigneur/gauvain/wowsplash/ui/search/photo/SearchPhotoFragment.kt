package com.seigneur.gauvain.wowsplash.ui.search.photo

import android.widget.Toast
import androidx.paging.PagedList
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.search.photo.SearchPhotoDataSource
import com.seigneur.gauvain.wowsplash.data.model.photo.Photo
import com.seigneur.gauvain.wowsplash.ui.base.paging.NetworkItemCallback
import com.seigneur.gauvain.wowsplash.ui.base.paging.adapter.BasePagedListAdapter
import com.seigneur.gauvain.wowsplash.ui.base.paging.fragment.BaseSearchPagingFragment
import com.seigneur.gauvain.wowsplash.ui.base.paging.viewModel.BaseSearchResultViewModel
import com.seigneur.gauvain.wowsplash.ui.list.photo.PhotoItemCallback
import com.seigneur.gauvain.wowsplash.ui.list.photo.SearchPhotoListAdapter
import com.seigneur.gauvain.wowsplash.ui.search.SearchViewModel
import kotlinx.android.synthetic.main.fragment_search_result.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.seigneur.gauvain.wowsplash.utils.event.EventObserver


class SearchPhotoFragment : BaseSearchPagingFragment<SearchPhotoDataSource, Long, Photo>(),
    PhotoItemCallback,
    NetworkItemCallback {

    private val mSearchViewModel by sharedViewModel<SearchViewModel>(from = { parentFragment!! })
    private val mSearchPhotoViewModel by viewModel<SearchPhotoViewModel>()

    private val mSearchPhotoListAdapter: SearchPhotoListAdapter by lazy {
        SearchPhotoListAdapter(this, this)
    }

    override val listAdapter: BasePagedListAdapter<*, *>
        get() = mSearchPhotoListAdapter

    override val vm: BaseSearchResultViewModel<SearchPhotoDataSource, Long, Photo>
        get() = mSearchPhotoViewModel

    override fun submitList(list: PagedList<Photo>) {
        mSearchPhotoListAdapter.submitList(list)

        if (mSearchPhotoListAdapter.itemCount ==0) {
            Toast.makeText(context, "no items found", Toast.LENGTH_LONG).show()
        }

    }

    override fun subscribeToLiveData() {
        mSearchViewModel.searchPhotoQuery.observe(viewLifecycleOwner, EventObserver {
            performSearch(it)
        })
    }

    override fun initAdapter() {
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS // todo - must define some stratgy with Glide before
        searchResultList.layoutManager = layoutManager
        searchResultList.adapter.let {
            searchResultList.adapter = mSearchPhotoListAdapter
        }

    }

    override fun onPhotoClicked(position: Int) {

    }

    override fun retry() {

    }

}
