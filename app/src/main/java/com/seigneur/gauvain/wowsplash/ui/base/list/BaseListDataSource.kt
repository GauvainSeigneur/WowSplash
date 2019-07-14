package com.seigneur.gauvain.wowsplash.ui.base.list

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

abstract class BaseListDataSource<Key, Value> (private val compositeDisposable: CompositeDisposable ): PageKeyedDataSource<Key, Value>() {


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


    override fun loadInitial(params: LoadInitialParams<Key>, callback: LoadInitialCallback<Key, Value>) {
        Timber.d("loadInitial called")
        // update network states.
        // we also provide an initial load state to the listeners so that the UI can know when the
        // very first list is loaded.
        networkState.postValue(NetworkState.LOADING)

        //todo - method to call here and be overridden in child
    }

    override fun loadAfter(params: LoadParams<Key>, callback: LoadCallback<Key, Value>) {
        Timber.d("loadAfter called")
        networkState.postValue(NetworkState.LOADING)

        //todo - method to call here and be overridden in child
    }

    fun retry() {
        if (retryCompletable != null) {
            compositeDisposable.add(
                retryCompletable!!
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
}