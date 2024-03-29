package com.seigneur.gauvain.wowsplash.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import com.seigneur.gauvain.wowsplash.R
import com.seigneur.gauvain.wowsplash.ui.base.BaseFragment
import com.seigneur.gauvain.wowsplash.ui.widget.SearchTextFieldView
import com.seigneur.gauvain.wowsplash.utils.hideKeyboard
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.view_search_textfield.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class SearchFragment : BaseFragment() {

    override val fragmentLayout: Int
        get() = R.layout.fragment_search

    val mSearchViewModel by viewModel<SearchViewModel>()

    private var mQueryEntry: String? = null

    lateinit var fragmentAdapter: SearchPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentAdapter = SearchPagerAdapter(childFragmentManager)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewPager()
        setUpSearchField()
    }

    private fun setUpSearchField(){
        searchTextFieldView.mSearchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                mQueryEntry = p0.toString()
            }
        })

        searchTextFieldView.mSearchEditText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                mSearchViewModel.updateSearchQuery(mQueryEntry)
               // true
            }
            false
        }


        searchTextFieldView.setOnSearchClickListener(object : SearchTextFieldView.OnSearchButtonListner {
            override fun onSearchClicked() {
                searchTextFieldView.hideKeyboard()
                mSearchViewModel.updateSearchQuery(mQueryEntry)
            }
        })

    }

    private fun setUpViewPager() {
        viewPager.offscreenPageLimit = 3
        viewPager.adapter = fragmentAdapter
        mTabs.setupWithViewPager(viewPager)
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                mSearchViewModel.currentFragmentPos = position
            }

        })
    }


}
