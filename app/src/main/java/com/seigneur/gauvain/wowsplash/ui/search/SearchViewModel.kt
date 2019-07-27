package com.seigneur.gauvain.wowsplash.ui.search

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.search.photo.SearchPhotoDataSourceFactory
import com.seigneur.gauvain.wowsplash.data.model.Photo
import com.seigneur.gauvain.wowsplash.data.repository.SearchRepository
import com.seigneur.gauvain.wowsplash.ui.base.BaseViewModel

class SearchViewModel(private val searchRepository: SearchRepository) : BaseViewModel() {

    companion object {
        const val PAGE_SIZE=15
    }

    var list: LiveData<PagedList<Photo>>? = null
    fun search(query: String) {

        val f = SearchPhotoDataSourceFactory(
            mDisposables,
            searchRepository,
            query
        )

        val conf = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setInitialLoadSizeHint(PAGE_SIZE)
            .setEnablePlaceholders(false)
            .build()
        list = LivePagedListBuilder(f, conf).build()
    }


}
