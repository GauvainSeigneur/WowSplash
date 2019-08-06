package com.seigneur.gauvain.wowsplash.ui.search.user

import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.base.BaseDataSourceFactory
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.search.collection.SearchCollectionDataSource
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.search.collection.SearchCollectionDataSourceFactory
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.search.user.SearchUserDataSource
import com.seigneur.gauvain.wowsplash.business.paginationInteractor.search.user.SearchUserDataSourceFactory
import com.seigneur.gauvain.wowsplash.data.model.photo.PhotoCollection
import com.seigneur.gauvain.wowsplash.data.model.user.User
import com.seigneur.gauvain.wowsplash.data.repository.SearchRepository
import com.seigneur.gauvain.wowsplash.data.repository.UserRepository
import com.seigneur.gauvain.wowsplash.ui.base.paging.viewModel.BaseSearchResultViewModel

class SearchUserViewModel(private val userRepository: UserRepository) :
    BaseSearchResultViewModel<SearchUserDataSource, Long, User>() {

    override fun createDataSourceFactory(query:String): BaseDataSourceFactory<SearchUserDataSource, Long, User> {
        return SearchUserDataSourceFactory(
            mDisposables,
            userRepository,
            query
        )
    }

    override fun initDataSource() {
        val conf = PagedList.Config.Builder()
            .setPageSize(15)
            .setInitialLoadSizeHint(15)
            .setEnablePlaceholders(false)
            .build()
        searchResultList = LivePagedListBuilder(factory as BaseDataSourceFactory<SearchUserDataSource, Long, User>, conf).build()
    }
}
