package com.seigneur.gauvain.wowsplash.data.model

data class SearchResult(
    val total:Int,
    val total_pages:Int,
    val results: List<Photo>
)