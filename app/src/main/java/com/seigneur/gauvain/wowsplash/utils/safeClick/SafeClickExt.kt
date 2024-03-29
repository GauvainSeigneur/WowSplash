package com.seigneur.gauvain.wowsplash.utils.safeClick

import android.view.View

fun View.setSafeOnClickListener(onSafeClick: (View) -> Unit) {
    val safeClickListener = SafeClickListener { view->
        onSafeClick(view)
    }
    setOnClickListener(safeClickListener)
}
