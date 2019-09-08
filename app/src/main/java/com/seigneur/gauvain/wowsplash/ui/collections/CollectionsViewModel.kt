package com.seigneur.gauvain.wowsplash.ui.collections

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.base.BaseDataSourceFactory
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.collection.CollectionDataSource
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.collection.CollectionDataSourceFactory
import com.seigneur.gauvain.wowsplash.data.model.photo.PhotoCollection
import com.seigneur.gauvain.wowsplash.data.repository.CollectionsRepository
import com.seigneur.gauvain.wowsplash.ui.base.paging.viewModel.BasePagingListViewModel
import com.seigneur.gauvain.wowsplash.utils.COLLECTION_LIST_ALL

class CollectionsViewModel(private val mCollectionsRepository: CollectionsRepository) :
    BasePagingListViewModel<CollectionDataSource, Long, PhotoCollection>(){

    var list: LiveData<PagedList<PhotoCollection>>? = null
    private var type: Int = COLLECTION_LIST_ALL
    private var config: PagedList.Config? = null
    private val collectionDataSourceFactory: CollectionDataSourceFactory by lazy {
        CollectionDataSourceFactory(
            mDisposables,
            mCollectionsRepository,
            type
        )
    }

    override val dataSourceFactory: BaseDataSourceFactory<CollectionDataSource, Long, PhotoCollection>
        get() = collectionDataSourceFactory

    fun init(inType: Int) {
        type = inType
        config.let {
            config = PagedList.Config.Builder()
                .setPageSize(15)
                .setInitialLoadSizeHint(15)
                .setEnablePlaceholders(false)
                .build()
            list = LivePagedListBuilder(collectionDataSourceFactory, config!!).build()
        }
    }

}
