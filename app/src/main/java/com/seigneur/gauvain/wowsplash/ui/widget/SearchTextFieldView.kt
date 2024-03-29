package com.seigneur.gauvain.wowsplash.ui.widget

import android.content.Context
import android.graphics.Paint
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.textfield.TextInputEditText
import com.seigneur.gauvain.wowsplash.R
import kotlinx.android.synthetic.main.view_search_textfield.view.*
import timber.log.Timber


class SearchTextFieldView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    val mSearchEditText: TextInputEditText
    private var isIconMicMode = true

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.view_search_textfield, this)
        this.background = ContextCompat.getDrawable(context, R.drawable.search_view_bg)
        mSearchEditText = searchEditText
        changeIcon(false)
        manageIconAppearance()
        setOnIconClick()
    }

    /**
     * manage icon type according to search
     */
    private fun manageIconAppearance() {
        mSearchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                changeIcon(false)
                p0?.let {
                    when (it.length) {
                        0 -> changeIcon(false)
                        else -> changeIcon(true)
                    }
                }
            }
        })
    }

    /**
     * change icon
     * must use AVD for smoother animation
     */
    private fun changeIcon(hasText: Boolean) {
        isIconMicMode = hasText
        if (hasText)
            clearSearchButton.visibility = View.VISIBLE
        else
            clearSearchButton.visibility = View.INVISIBLE
    }

    /**
     * Listen icon click
     */
    private fun setOnIconClick() {
        clearSearchButton.setOnClickListener {
            mSearchEditText.text?.clear()
        }

    }

    fun setOnSearchClickListener(onSearchButtonListner : OnSearchButtonListner) {
        searchButton.setOnClickListener {
            onSearchButtonListner.onSearchClicked()
        }
    }

    interface OnSearchButtonListner {
        fun onSearchClicked()
    }

}
