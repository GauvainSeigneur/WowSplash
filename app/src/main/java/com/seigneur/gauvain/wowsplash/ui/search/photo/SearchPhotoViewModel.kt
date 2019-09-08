package com.seigneur.gauvain.wowsplash.ui.search.photo

import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.base.BaseDataSourceFactory
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.search.photo.SearchPhotoDataSource
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.search.photo.SearchPhotoDataSourceFactory
import com.seigneur.gauvain.wowsplash.data.model.photo.Photo

import com.seigneur.gauvain.wowsplash.data.repository.SearchRepository
import com.seigneur.gauvain.wowsplash.ui.base.paging.viewModel.BaseSearchResultViewModel

class SearchPhotoViewModel(private val searchRepository: SearchRepository) :
    BaseSearchResultViewModel<SearchPhotoDataSource, Long, Photo>() {

    override fun createDataSourceFactory(query:String): BaseDataSourceFactory<SearchPhotoDataSource, Long, Photo> {
        return SearchPhotoDataSourceFactory(
            mDisposables,
            searchRepository,
            query
        )
    }

    override fun initDataSource() {
        val conf = PagedList.Config.Builder()
            .setPageSize(15)
            .setInitialLoadSizeHint(15)
            .setEnablePlaceholders(false)
            .build()
        searchResultList = LivePagedListBuilder(factory as BaseDataSourceFactory<SearchPhotoDataSource, Long, Photo>, conf).build()
    }
}
