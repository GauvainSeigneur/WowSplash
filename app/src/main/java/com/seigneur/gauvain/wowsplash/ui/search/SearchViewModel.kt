package com.seigneur.gauvain.wowsplash.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.seigneur.gauvain.wowsplash.business.interactor.SearchInteractor
import com.seigneur.gauvain.wowsplash.business.interactor.pagedList.SearchPhotoDataSourceFactory
import com.seigneur.gauvain.wowsplash.business.interactor.pagedList.SearchPhotosDataSource
import com.seigneur.gauvain.wowsplash.business.result.SearchResult
import com.seigneur.gauvain.wowsplash.data.model.Photo
import com.seigneur.gauvain.wowsplash.data.model.PhotoCollection
import com.seigneur.gauvain.wowsplash.data.model.SearchResponse
import com.seigneur.gauvain.wowsplash.data.model.network.NetworkState
import com.seigneur.gauvain.wowsplash.data.model.user.User
import com.seigneur.gauvain.wowsplash.data.repository.SearchRepository
import com.seigneur.gauvain.wowsplash.ui.base.BaseViewModel
import com.seigneur.gauvain.wowsplash.ui.home.PhotoViewModel
import timber.log.Timber

class SearchViewModel(private val searchRepository: SearchRepository) : BaseViewModel(),
    SearchInteractor.SearchCallback {


    var list: LiveData<PagedList<Photo>>? = null
    private var photoListType: String="yolo"

    private var config: PagedList.Config? = null
    private val photoDataSourceFactory: SearchPhotoDataSourceFactory by lazy {
        SearchPhotoDataSourceFactory(
            mDisposables,
            searchRepository,
            photoListType
        )
    }

    val networkState: LiveData<NetworkState>
        get() = Transformations.switchMap(photoDataSourceFactory.photoDataSourceLiveData)
        { it.networkState }

    val refreshState: LiveData<NetworkState>
        get() = Transformations.switchMap(photoDataSourceFactory.photoDataSourceLiveData) {
            Timber.d("refresh called ")
            it.initialLoad
        }

    fun init(photoType: String) {
        config.let {
            config = PagedList.Config.Builder()
                .setPageSize(30)
                .setInitialLoadSizeHint(30)
                .setEnablePlaceholders(false)
                .build()
            list = LivePagedListBuilder(photoDataSourceFactory, config!!).build()
        }
        Timber.d("init called ${list}")
    }








    val searchResult = MutableLiveData<SearchResult>()

    val searchInteractor by lazy {
        SearchInteractor(searchRepository, mDisposables, this)
    }

    override fun onSearchPhotoSuccess(searchResponse: SearchResponse<Photo>) {
        searchResult.value = SearchResult.searchPhoto(searchResponse)
        Timber.d("photos founded $searchResponse")
    }

    override fun onSearchCollectionSuccess(searchResponse: SearchResponse<PhotoCollection>) {

    }

    override fun onSearchUserSuccess(searchResponse: SearchResponse<User>) {

    }

    override fun onError(throwable: Throwable) {

    }

    fun searchPhoto() {
        searchInteractor.searchPhotos("yolo")
    }

}
