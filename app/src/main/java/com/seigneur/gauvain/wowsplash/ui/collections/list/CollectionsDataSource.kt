package com.seigneur.gauvain.wowsplash.ui.collections.list

import com.seigneur.gauvain.wowsplash.data.model.PhotoCollection
import com.seigneur.gauvain.wowsplash.data.repository.CollectionsRepository
import com.seigneur.gauvain.wowsplash.ui.base.pagingList.dataSource.BaseListDataSource
import com.seigneur.gauvain.wowsplash.data.model.network.NetworkState
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action

class CollectionsDataSource
    internal constructor(private val compositeDisposable: CompositeDisposable,
                         private val mCollectionsRepository: CollectionsRepository) :
    BaseListDataSource<Long, PhotoCollection>(compositeDisposable) {

    override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<Long, PhotoCollection>) {
        super.loadInitial(params, callback)
        compositeDisposable.add(
            mCollectionsRepository.getCollections(1, params.requestedLoadSize)
                .subscribe(
                    { shots ->
                        // clear retry since last request succeeded
                        setRetry(null)
                        networkState.postValue(NetworkState.LOADED)
                        initialLoad.postValue(NetworkState.LOADED)
                        callback.onResult(shots, null, 2L)
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

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, PhotoCollection>) {
        super.loadAfter(params, callback)
        compositeDisposable.add(mCollectionsRepository.getCollections( params.key, params.requestedLoadSize)
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
                    setRetry(Action { loadAfter(params, callback) }) //todo - check it
                    // publish the error
                    networkState.postValue(NetworkState.error(throwable.message))
                }
            )
        )
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, PhotoCollection>) {
        // ignored, since we only ever append to our initial load
    }


}
