package com.seigneur.gauvain.wowsplash.ui.collections

import com.seigneur.gauvain.wowsplash.data.model.PhotoCollection
import com.seigneur.gauvain.wowsplash.data.repository.CollectionsRepository
import com.seigneur.gauvain.wowsplash.ui.base.pagingList.dataSource.BaseListDataSourceFactory
import com.seigneur.gauvain.wowsplash.ui.base.pagingList.viewModel.BasePagedListViewModel
import com.seigneur.gauvain.wowsplash.ui.collections.list.CollectionsDataSourceFactory


class CollectionsViewModel(private val mCollectionsRepository: CollectionsRepository) :
    BasePagedListViewModel<Long, PhotoCollection>(15) {

    private val collectionsDataSourceFactory: CollectionsDataSourceFactory by lazy {
        CollectionsDataSourceFactory(mDisposables, mCollectionsRepository)
    }

    override val dataSourceFactory: BaseListDataSourceFactory<Long, PhotoCollection>
        get() = collectionsDataSourceFactory

}
