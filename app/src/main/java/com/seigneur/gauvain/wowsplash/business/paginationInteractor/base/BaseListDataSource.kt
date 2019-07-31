package com.seigneur.gauvain.wowsplash.business.paginationInteractor.base

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

/**
 * Base class for pagingDataSource
 * it allows to perform same behavior on each pagingDataSource and reuse same code
 */
abstract class BaseListDataSource<T, Key, Value>(private val compositeDisposable: CompositeDisposable) :
    PageKeyedDataSource<Key, Value>() {

    val initialLoad = MutableLiveData<NetworkState>()

    /*
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
     * Define request for initial and after request
     */
    abstract fun loadInitialRequest(keyPage: Key, pageSize: Int): Flowable<T>

    abstract fun loadAfterRequest(keyPage: Key, pageSize: Int): Flowable<T>
    /**
     * Define callback result from request (if you have a request which an object a value different of @Value
     * This method allows you to override the result and return the right object
     */
    abstract fun handleCallback(expectedResponse: T): List<Value>

    /**
     * Define page for each request
     */
    abstract val firstPageKey: Key
    abstract val firstNextPageKey: Key
    abstract fun nextKey(currentKey: Key): Key

    override fun loadInitial(params: LoadInitialParams<Key>, callback: LoadInitialCallback<Key, Value>) {
        // update network states.
        // we also provide an initial load state to the listeners so that the UI can know when the
        // very first list is loaded.
        networkState.postValue(NetworkState.LOADING)
        initialLoad.postValue(NetworkState.LOADING)
        handleFirstLoad(params, callback)
    }

    override fun loadAfter(params: LoadParams<Key>, callback: LoadCallback<Key, Value>) {
        networkState.postValue(NetworkState.LOADING)
        handleLoadAfter(params, callback)
    }

    override fun loadBefore(params: LoadParams<Key>, callback: LoadCallback<Key, Value>) {}

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

    private fun setRetry(action: Action?) {
        if (action == null) {
            this.retryCompletable = null
        } else {
            this.retryCompletable = Completable.fromAction(action)
        }
    }

    private fun handleFirstLoad(
        params: LoadInitialParams<Key>,
        callback: LoadInitialCallback<Key, Value>
    ) {
        compositeDisposable.add(
            loadInitialRequest(firstPageKey, params.requestedLoadSize)
                .subscribe(
                    {
                        // clear retry since last request succeeded
                        setRetry(null)
                        networkState.postValue(NetworkState.LOADED)
                        initialLoad.postValue(NetworkState.LOADED)
                        callback.onResult(handleCallback(it), null, firstNextPageKey)
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

    private fun handleLoadAfter(
        params: LoadParams<Key>,
        callback: LoadCallback<Key, Value>
    ) {

        Timber.d("handleLoadAfter called")
        compositeDisposable.add(
            loadAfterRequest(params.key, params.requestedLoadSize)
                .subscribe(
                    {
                        //val nextKey = nextKey//(params.key) as Long + 1
                        // clear retry since last request succeeded
                        setRetry(null)
                        networkState.postValue(NetworkState.LOADED)
                        callback.onResult(handleCallback(it), nextKey(params.key))
                        Timber.d("handleLoadAfter second next key ${nextKey(params.key)}")
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