package com.seigneur.gauvain.wowsplash.data.model.photo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Links(
    var self: String ="Unknown",
    var html: String? ="Unknown",
    var download: String? ="Unknown",
    var download_location: String?="Unknown"
):Parcelable