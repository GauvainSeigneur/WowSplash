package com.seigneur.gauvain.wowsplash.data.model

data class SearchResponse<T>(
    val total:Int,
    val total_pages:Int,
    val results: List<T>
)