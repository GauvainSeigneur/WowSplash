package com.seigneur.gauvain.wowsplash.ui.home.list

import androidx.paging.PageKeyedDataSource
import com.seigneur.gauvain.wowsplash.data.model.Photo
import com.seigneur.gauvain.wowsplash.data.repository.PhotoRepository
import com.seigneur.gauvain.wowsplash.ui.base.pagingList.dataSource.BaseListDataSource
import com.seigneur.gauvain.wowsplash.data.model.network.NetworkState

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action

class PhotosDataSource
internal constructor(
    private val compositeDisposable: CompositeDisposable,
    private val mPhotoRepository: PhotoRepository
) : BaseListDataSource<Long, Photo>(compositeDisposable) {

    override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<Long, Photo>) {
        super.loadInitial(params, callback)

        compositeDisposable.add(
            mPhotoRepository.getPhotos(1, params.requestedLoadSize)
                    .subscribe(
                        { photos ->
                            // clear retry since last request succeeded
                            setRetry(null)
                            networkState.postValue(NetworkState.LOADED)
                            initialLoad.postValue(NetworkState.LOADED)
                            callback.onResult(photos, null, 2L)
                        },
                        { throwable ->
                            // keep a Completable for future retry
                            setRetry(Action { loadInitial(params, callback) })
                            val error = NetworkState.error(throwable.message)
                            // publish the error
                            networkState.postValue(error)
                            initialLoad.postValue(error)
                        }
                    )
                )
    }

    override fun loadBefore(
        params: PageKeyedDataSource.LoadParams<Long>,
        callback: PageKeyedDataSource.LoadCallback<Long, Photo>
    ) {
        // ignored, since we only ever append to our initial load
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, Photo>) {
        super.loadAfter(params, callback)
        compositeDisposable.add(mPhotoRepository.getPhotos(params.key, params.requestedLoadSize)
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
