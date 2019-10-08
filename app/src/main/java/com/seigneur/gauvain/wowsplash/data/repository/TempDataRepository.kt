package com.seigneur.gauvain.wowsplash.data.repository

import androidx.lifecycle.MutableLiveData
import com.seigneur.gauvain.wowsplash.data.model.photo.PhotoItem
import com.seigneur.gauvain.wowsplash.utils.event.Event

class TempDataRepository{
    val photoItemModifiedFromDetails=MutableLiveData<Event<PhotoItem>>()
    val photoItemModifiedFromAddCollection = MutableLiveData<Event<BookmarkedItem>>()

    class BookmarkedItem(val position:Int, val bookmarked:Boolean )
}
