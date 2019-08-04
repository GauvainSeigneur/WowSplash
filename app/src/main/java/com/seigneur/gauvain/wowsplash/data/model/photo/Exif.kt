package com.seigneur.gauvain.wowsplash.data.model.photo

data class Exif(
    val make: String? ="unknown",
    val model: String? ="unknown",
    val exposure_time: Long? = Long.MIN_VALUE,
    val aperture : Long? = Long.MIN_VALUE,
    val focal_length: Int?=-1,
    val iso: Int?=-1
)