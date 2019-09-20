package com.seigneur.gauvain.wowsplash.ui.photo

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PreCachingLayoutManager
@JvmOverloads
constructor(
    context: Context,
    orientation: Int?,
    reverseLayout: Boolean?
) : LinearLayoutManager(context) {
    private val defaultExtraLayoutSpace = 600
    private var extraLayoutSpace = -1

    fun setExtraLayoutSpace(extraLayoutSpace: Int) {
        this.extraLayoutSpace = extraLayoutSpace
    }

    override fun getExtraLayoutSpace(state: RecyclerView.State): Int {
        return if (extraLayoutSpace > 0) {
            extraLayoutSpace
        } else defaultExtraLayoutSpace
    }
}