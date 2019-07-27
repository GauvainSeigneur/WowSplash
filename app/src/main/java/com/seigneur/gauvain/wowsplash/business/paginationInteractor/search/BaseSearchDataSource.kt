package com.seigneur.gauvain.wowsplash.business.paginationInteractor.search

import com.seigneur.gauvain.wowsplash.business.paginationInteractor.base.BaseListDataSource
import com.seigneur.gauvain.wowsplash.data.model.SearchResponse
import io.reactivex.disposables.CompositeDisposable

abstract class BaseSearchDataSource<T>
internal constructor(
    compositeDisposable: CompositeDisposable
) : BaseListDataSource<SearchResponse<T>, Long, T>(compositeDisposable) {

    override val firstPageKey: Long
        get() = 1L

    override val firstNextPageKey: Long
        get() = 2L

    override fun nextKey(currentKey: Long): Long {
        return currentKey+1L
    }

    override fun handleCallback(expectedResponse: SearchResponse<T>): List<T> {
        return expectedResponse.results
    }
}
