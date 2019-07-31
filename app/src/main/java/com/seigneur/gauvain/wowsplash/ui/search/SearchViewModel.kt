package com.seigneur.gauvain.wowsplash.ui.search

import androidx.lifecycle.MutableLiveData
import com.seigneur.gauvain.wowsplash.ui.base.BaseViewModel

class SearchViewModel : BaseViewModel() {

    //val searchPhoto = MutableLiveData<String>()

    var currentFragmentPos = 0
    val searchQuery =  MutableLiveData<Pair<Int, String>>()

}
