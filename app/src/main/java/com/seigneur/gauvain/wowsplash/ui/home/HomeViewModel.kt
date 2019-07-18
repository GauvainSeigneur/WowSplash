package com.seigneur.gauvain.wowsplash.ui.home

import com.seigneur.gauvain.wowsplash.data.model.Photo
import com.seigneur.gauvain.wowsplash.data.repository.PhotoRepository
import com.seigneur.gauvain.wowsplash.ui.base.pagingList.viewModel.BasePagedListViewModel
import com.seigneur.gauvain.wowsplash.ui.base.pagingList.dataSource.BaseListDataSourceFactory
import com.seigneur.gauvain.wowsplash.ui.home.list.PhotoDataSourceFactory

class HomeViewModel(private val mPhotoRepository: PhotoRepository) :
    BasePagedListViewModel<Long, Photo>(15) {

    private val photoDataSourceFactory: PhotoDataSourceFactory by lazy {
        PhotoDataSourceFactory(mDisposables, mPhotoRepository)
    }

    override val dataSourceFactory: BaseListDataSourceFactory<Long, Photo>
        get() = photoDataSourceFactory

}
