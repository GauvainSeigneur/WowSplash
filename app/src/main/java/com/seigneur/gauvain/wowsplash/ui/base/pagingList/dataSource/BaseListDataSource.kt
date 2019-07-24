package com.seigneur.gauvain.wowsplash.ui.base.pagingList.dataSource

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.seigneur.gauvain.wowsplash.data.model.network.NetworkState
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

abstract class BaseListDataSource<Key, Value>(private val compositeDisposable: CompositeDisposable) :
    PageKeyedDataSource<Key, Value>() {

    val initialLoad = MutableLiveData<NetworkState>()

    /*
    * Step 1: Initialize the restApiFactory.
    * The networkState and initialLoading variables
    * are for updating the UI when data is being fetched
    * by displaying a progress bar
    */
    val networkState = MutableLiveData<NetworkState>()

    /**
     * Keep Completable reference for the retry event
     */
    private var retryCompletable: Completable? = null


    /**
     * Must call onSuper in child class
     */
    override fun loadInitial(params: LoadInitialParams<Key>, callback: LoadInitialCallback<Key, Value>) {
        Timber.d("loadInitial called")
        // update network states.
        // we also provide an initial load state to the listeners so that the UI can know when the
        // very first list is loaded.
        networkState.postValue(NetworkState.LOADING)
        initialLoad.postValue(NetworkState.LOADING)
    }

    /**
     * Must call onSuper in child class
     */
    override fun loadAfter(params: LoadParams<Key>, callback: LoadCallback<Key, Value>) {
        Timber.d("loadAfter called")
        networkState.postValue(NetworkState.LOADING)
    }

    /**
     * Retry completable
     */
    fun retry() {
        retryCompletable?.let {
            compositeDisposable.add(
                it
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ }, { throwable -> Timber.e(throwable.message) })
            )
        }

    }

    fun setRetry(action: Action?) {
        if (action == null) {
            this.retryCompletable = null
        } else {
            this.retryCompletable = Completable.fromAction(action)
        }
    }


    fun handleFirstLoad(
        request: Flowable<List<Value>>,
        params: LoadInitialParams<Key>,
        callback: LoadInitialCallback<Key, Value>,
        nextPageKey: Key
    ) {
        compositeDisposable.add(request
            .subscribe(
                {
                    // clear retry since last request succeeded
                    setRetry(null)
                    networkState.postValue(NetworkState.LOADED)
                    initialLoad.postValue(NetworkState.LOADED)
                    callback.onResult(it, null, nextPageKey)
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

    fun handleLoadAfter(
        request: Flowable<List<Value>>,
        params: LoadParams<Key>,
        callback: LoadCallback<Key, Value>,
        nextPageKey: Key
    ) {
        compositeDisposable.add(
            request
                .subscribe(
                    { nextList ->
                        val nextKey = nextPageKey//params.key + 1
                        // clear retry since last request succeeded
                        setRetry(null)
                        networkState.postValue(NetworkState.LOADED)
                        callback.onResult(nextList, nextKey)
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