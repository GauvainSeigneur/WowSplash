package com.seigneur.gauvain.wowsplash.data.model.photo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PhotoUrl(
    //Fallback in case of is null,
    var thumb: String,
    var small: String? = thumb,
    var regular: String? = small,
    var full: String? = regular,
    var raw: String? = full
):Parcelable