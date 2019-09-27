package com.seigneur.gauvain.wowsplash.data.model.user

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProfileImage(
    var small: String,
    var medium: String? = small,
    var large: String? = medium
):Parcelable