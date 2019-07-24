package com.seigneur.gauvain.wowsplash.business.interactor

import com.seigneur.gauvain.wowsplash.data.model.Photo
import com.seigneur.gauvain.wowsplash.data.model.PhotoCollection
import com.seigneur.gauvain.wowsplash.data.model.SearchResponse
import com.seigneur.gauvain.wowsplash.data.model.user.User
import com.seigneur.gauvain.wowsplash.data.repository.SearchRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

/**
 *  Must be used only by ViewModel which inject all dependencies
 */
class SearchInteractor(
    private val searchRepository: SearchRepository,
    private val compositeDisposable: CompositeDisposable,
    private val searchCallback: SearchCallback
) {

    fun searchPhotos(query: String) {
        compositeDisposable.add(
            searchRepository.searchPhotos(query)
                .subscribeBy (
                    onSuccess = {
                        searchCallback.onSearchPhotoSuccess(it)
                    },
                    onError = {
                        searchCallback.onError(it)
                    }
                )
        )
    }

    fun searchCollections(query: String) {
        compositeDisposable.add(
            searchRepository.searchCollection(query)
                .subscribeBy (
                    onSuccess = {
                        searchCallback.onSearchCollectionSuccess(it)
                    },
                    onError = {
                        searchCallback.onError(it)
                    }
                )
        )
    }

    fun searchUser(query: String) {
        compositeDisposable.add(
            searchRepository.searchUser(query)
                .subscribeBy (
                    onSuccess = {
                        searchCallback.onSearchUserSuccess(it)
                    },
                    onError = {
                        searchCallback.onError(it)
                    }
                )
        )
    }

    interface SearchCallback {
        fun onSearchPhotoSuccess(searchResponse: SearchResponse<Photo>)
        fun onSearchCollectionSuccess(searchResponse: SearchResponse<PhotoCollection>)
        fun onSearchUserSuccess(searchResponse: SearchResponse<User>)
        fun onError(throwable: Throwable)
    }
}

