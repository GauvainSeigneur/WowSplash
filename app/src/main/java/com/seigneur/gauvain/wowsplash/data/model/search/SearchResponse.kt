package com.seigneur.gauvain.wowsplash.data.model.search

data class SearchResponse<T>(
    val total:Int,
    val total_pages:Int,
    val results: List<T>
)