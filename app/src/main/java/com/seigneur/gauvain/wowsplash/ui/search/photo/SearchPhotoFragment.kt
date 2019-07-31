package com.seigneur.gauvain.wowsplash.ui.search.photo

import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.search.photo.SearchPhotoDataSource
import com.seigneur.gauvain.wowsplash.data.model.Photo
import com.seigneur.gauvain.wowsplash.ui.base.paging.NetworkItemCallback
import com.seigneur.gauvain.wowsplash.ui.base.paging.adapter.BasePagedListAdapter
import com.seigneur.gauvain.wowsplash.ui.base.paging.fragment.BaseSearchPagingFragment
import com.seigneur.gauvain.wowsplash.ui.base.paging.viewModel.BaseSearchResultViewModel
import com.seigneur.gauvain.wowsplash.ui.home.list.PhotoItemCallback
import com.seigneur.gauvain.wowsplash.ui.home.list.PhotoListAdapter
import com.seigneur.gauvain.wowsplash.ui.search.SearchViewModel
import kotlinx.android.synthetic.main.fragment_refresh_list.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class SearchPhotoFragment : BaseSearchPagingFragment<SearchPhotoDataSource, Long, Photo>(), PhotoItemCallback, NetworkItemCallback {

    private val mSearchViewModel by sharedViewModel<SearchViewModel>(from = { parentFragment!! })
    private val mSearchPhotoViewModel by viewModel<SearchPhotoViewModel>()
    private lateinit var mGridLayoutManager: GridLayoutManager
    private val photoListAdapter: PhotoListAdapter by lazy {
        PhotoListAdapter(this, this)
    }

    override val listAdapter: BasePagedListAdapter<*, *>
        get() = photoListAdapter

    override val vm: BaseSearchResultViewModel<SearchPhotoDataSource, Long, Photo>
        get() = mSearchPhotoViewModel

    override fun submitList(list: PagedList<Photo>) {
        Timber.d("submit list called")
        photoListAdapter.submitList(list)
    }

    override fun subscribeToLiveData() {
        mSearchViewModel.searchQuery.observe(viewLifecycleOwner, Observer<Pair<Int, String>> {
            when(it.first) {
                0 ->   performSearch(it.second)
            }
        })
    }

    override fun initAdapter() {
        if (photoList.layoutManager == null && photoList.adapter == null) {
            mGridLayoutManager = GridLayoutManager(context, 1)
            photoList.layoutManager = GridLayoutManager(context, 1)
            photoList.adapter = photoListAdapter
        }
    }

    override fun onShotClicked(position: Int) {

    }

    override fun retry() {

    }

}
