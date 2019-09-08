package com.seigneur.gauvain.wowsplash.ui.search.collection

import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.base.BaseDataSourceFactory
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.search.collection.SearchCollectionDataSource
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.search.collection.SearchCollectionDataSourceFactory
import com.seigneur.gauvain.wowsplash.data.model.photo.PhotoCollection
import com.seigneur.gauvain.wowsplash.data.repository.SearchRepository
import com.seigneur.gauvain.wowsplash.ui.base.paging.viewModel.BaseSearchResultViewModel

class SearchCollectionViewModel(private val searchRepository: SearchRepository) :
    BaseSearchResultViewModel<SearchCollectionDataSource, Long, PhotoCollection>() {

    override fun createDataSourceFactory(query:String): BaseDataSourceFactory<SearchCollectionDataSource, Long, PhotoCollection> {
        return SearchCollectionDataSourceFactory(
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
        searchResultList = LivePagedListBuilder(factory as BaseDataSourceFactory<SearchCollectionDataSource, Long, PhotoCollection>, conf).build()
    }
}
