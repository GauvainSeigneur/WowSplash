package com.seigneur.gauvain.wowsplash.business.result

import com.seigneur.gauvain.wowsplash.data.model.Photo
import com.seigneur.gauvain.wowsplash.data.model.PhotoCollection
import com.seigneur.gauvain.wowsplash.data.model.SearchResponse
import com.seigneur.gauvain.wowsplash.data.model.user.User

sealed class SearchResult {
    data class searchUser(val searchResponse: SearchResponse<User>) : SearchResult()
    data class searchCollection(val searchResponse: SearchResponse<PhotoCollection>) : SearchResult()
    data class searchPhoto(val searchResponse: SearchResponse<Photo>) : SearchResult()
    data class searchError(val throwable: Throwable) : SearchResult()
}