package com.seigneur.gauvain.wowsplash.ui.search

import androidx.lifecycle.MutableLiveData
import com.seigneur.gauvain.wowsplash.data.model.SearchResult
import com.seigneur.gauvain.wowsplash.data.repository.PhotoRepository
import com.seigneur.gauvain.wowsplash.ui.base.BaseViewModel
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber

class SearchViewModel(private val mPhotoRepository: PhotoRepository) : BaseViewModel() {

    var mSearchResult = MutableLiveData<PhotoSearchResult>()
    /**
     * Get photos
     */
    fun searchPhotos(query:String) {
        mDisposables.add(mPhotoRepository.searchPhotos(query)
            .subscribeBy(
                onSuccess = {
                    Timber.d("search onNext $it")
                    mSearchResult.value = PhotoSearchResult.SearchSuccess(it)
                    //mListResult.value = ListResult(inList = it)
                },
                onError = {
                    Timber.d("search onError $it")
                    mSearchResult.value = PhotoSearchResult.SearchError(it)
                    //mListResult.value = ListResult(inError = it)
                }
            )
        )
    }

    sealed class PhotoSearchResult {
        data class SearchSuccess(val result: SearchResult? = null) : PhotoSearchResult()
        data class SearchError(val inError: Throwable? = null) : PhotoSearchResult()
    }

}
