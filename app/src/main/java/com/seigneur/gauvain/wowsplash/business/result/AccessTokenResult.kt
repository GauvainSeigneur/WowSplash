package com.seigneur.gauvain.wowsplash.business.result

sealed class AccessTokenResult {
    data class Fetched(val token:String) : AccessTokenResult()
    data class UnFetched(val messsage:String?=null) : AccessTokenResult()
}