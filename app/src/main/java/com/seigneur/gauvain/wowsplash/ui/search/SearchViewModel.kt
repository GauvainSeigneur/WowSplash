package com.seigneur.gauvain.wowsplash.ui.search

import androidx.lifecycle.MutableLiveData
import com.seigneur.gauvain.wowsplash.business.interactor.SearchInteractor
import com.seigneur.gauvain.wowsplash.business.result.SearchResult
import com.seigneur.gauvain.wowsplash.data.model.Photo
import com.seigneur.gauvain.wowsplash.data.model.PhotoCollection
import com.seigneur.gauvain.wowsplash.data.model.SearchResponse
import com.seigneur.gauvain.wowsplash.data.model.user.User
import com.seigneur.gauvain.wowsplash.data.repository.SearchRepository
import com.seigneur.gauvain.wowsplash.ui.base.BaseViewModel
import timber.log.Timber

class SearchViewModel(private val searchRepository: SearchRepository) : BaseViewModel(),
    SearchInteractor.SearchCallback {

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
