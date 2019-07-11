package com.seigneur.gauvain.wowsplash.data.model

data class AccessToken(
    val access_token: String,
    val token_type: String,
    val scope: String,
    val created_at: String
)