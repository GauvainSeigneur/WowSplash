package com.seigneur.gauvain.wowsplash.data.model.user

data class ProfileImage(
    var small: String,
    var medium: String? =small,
    var large: String? =medium
)