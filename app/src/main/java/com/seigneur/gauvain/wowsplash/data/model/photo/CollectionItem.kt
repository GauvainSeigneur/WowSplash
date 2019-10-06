package com.seigneur.gauvain.wowsplash.data.model.photo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CollectionItem(
    val photoCollection: PhotoCollection,
    var selected: Boolean = false
) :Parcelable