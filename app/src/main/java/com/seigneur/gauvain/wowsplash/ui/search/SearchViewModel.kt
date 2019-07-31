package com.seigneur.gauvain.wowsplash.ui.search

import androidx.lifecycle.MutableLiveData
import com.seigneur.gauvain.wowsplash.ui.base.BaseViewModel

class SearchViewModel : BaseViewModel() {

    var currentFragmentPos = 0
    val searchQuery =  MutableLiveData<Pair<Int, String>>()

}
