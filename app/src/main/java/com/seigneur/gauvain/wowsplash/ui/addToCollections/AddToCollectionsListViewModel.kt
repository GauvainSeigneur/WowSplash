package com.seigneur.gauvain.wowsplash.ui.addToCollections

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.base.BaseDataSourceFactory
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.userCollections.UserCollectionsDataSource
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.userCollections.UserCollectionsDataSourceFactory
import com.seigneur.gauvain.wowsplash.data.model.photo.PhotoCollection
import com.seigneur.gauvain.wowsplash.data.repository.CollectionsRepository
import com.seigneur.gauvain.wowsplash.ui.base.paging.viewModel.BaseUserCollectionPagingListViewModel

class AddToCollectionsListViewModel(
    private val collectionsRepository: CollectionsRepository
) : BaseUserCollectionPagingListViewModel<UserCollectionsDataSource, Long, PhotoCollection>() {

    companion object {
        private const val pageSize = 50
    }

    override fun createDataSourceFactory(name:String): BaseDataSourceFactory<UserCollectionsDataSource, Long, PhotoCollection> {
        return UserCollectionsDataSourceFactory(
            name,
            mDisposables,
            collectionsRepository
        )
    }

    override fun initDataSource() {
        val conf = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize)
            .setEnablePlaceholders(false)
            .build()
        list = LivePagedListBuilder(factory as BaseDataSourceFactory<UserCollectionsDataSource, Long, PhotoCollection>, conf).build()
    }
}
