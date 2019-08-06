package com.seigneur.gauvain.wowsplash.ui.search

import androidx.lifecycle.MutableLiveData
import com.seigneur.gauvain.wowsplash.ui.base.BaseViewModel
import com.seigneur.gauvain.wowsplash.utils.event.Event

class SearchViewModel : BaseViewModel() {

    var currentFragmentPos = 0
    val searchPhotoQuery=MutableLiveData<Event<String>>()
    val searchCollectionQuery=MutableLiveData<Event<String>>()
    val searchUserQuery=MutableLiveData<Event<String>>()

    fun updateSearchQuery(query:String) {
        searchPhotoQuery.postValue(Event(query))
        searchCollectionQuery.postValue(Event(query))
        searchUserQuery.postValue(Event(query))
    }

}
