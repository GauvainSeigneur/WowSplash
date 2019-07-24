package com.seigneur.gauvain.wowsplash.business.interactor.pagedList

import com.seigneur.gauvain.wowsplash.data.model.Photo
import com.seigneur.gauvain.wowsplash.data.repository.PhotoRepository
import com.seigneur.gauvain.wowsplash.ui.base.pagingList.dataSource.BaseListDataSource
import com.seigneur.gauvain.wowsplash.data.model.network.NetworkState

import com.seigneur.gauvain.wowsplash.utils.PHOTO_LIST_HOME
import com.seigneur.gauvain.wowsplash.utils.PHOTO_LIST_SEARCH
import io.reactivex.Flowable

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import org.reactivestreams.Publisher

class PhotosDataSource
internal constructor(
    private val compositeDisposable: CompositeDisposable,
    private val mPhotoRepository: PhotoRepository,
    private val listType: Int,
    private val orderBy: String?,
    private val query: String?
) : BaseListDataSource<Long, Photo>(compositeDisposable) {

    override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<Long, Photo>) {
        super.loadInitial(params, callback)
        when (listType) {
            PHOTO_LIST_HOME -> {
                //getFirstHomeListPage(params, callback)
                handleFirstLoad(
                    mPhotoRepository.getPhotos(1, params.requestedLoadSize, orderBy),
                    params,
                    callback,
                    2L
                )
            }
            PHOTO_LIST_SEARCH -> {

            }
        }
    }

    override fun loadBefore(
        params: LoadParams<Long>,
        callback: LoadCallback<Long, Photo>
    ) {
        // ignored, since we only ever append to our initial load
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, Photo>) {
        super.loadAfter(params, callback)
        when (listType) {
            PHOTO_LIST_HOME -> {
                handleLoadAfter(mPhotoRepository.getPhotos(1,
                    params.requestedLoadSize, orderBy),
                    params, callback,
                    params.key+1)
            }
            PHOTO_LIST_SEARCH -> {

            }
        }
    }

    private fun getAfterHomeLIst(params: LoadParams<Long>, callback: LoadCallback<Long, Photo>) {
        compositeDisposable.add(mPhotoRepository.getPhotos(params.key, params.requestedLoadSize, orderBy)
            .subscribe(
                { shots ->
                    //long nextKey = (params.key == shots.body().getTotalResults()) ? null : params.key+1; //TODO - to reactivate
                    val nextKey = params.key + 1
                    // clear retry since last request succeeded
                    setRetry(null)
                    networkState.postValue(NetworkState.LOADED)
                    callback.onResult(shots, nextKey)
                },
                { throwable ->
                    // keep a Completable for future retry
                    setRetry(Action { loadAfter(params, callback) })
                    // publish the error
                    networkState.postValue(NetworkState.error(throwable.message))
                }
            )
        )
    }

}
