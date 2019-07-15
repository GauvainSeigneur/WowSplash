package com.seigneur.gauvain.wowsplash.ui.base.list

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import io.reactivex.Completable
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
        val vRetryComp = retryCompletable
        vRetryComp?.let {
            compositeDisposable.add(
                vRetryComp
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
}