package com.seigneur.gauvain.wowsplash.ui.search

import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.seigneur.gauvain.wowsplash.R
import com.seigneur.gauvain.wowsplash.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class SearchFragment : BaseFragment() {

    override val fragmentLayout: Int
        get() = R.layout.fragment_search

    val mSearchViewModel by viewModel<SearchViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentAdapter = SearchPagerAdapter(childFragmentManager)
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


        searchBtn.setOnClickListener {
            //mSearchViewModel.searchPhoto.value = "beach"
            mSearchViewModel.searchQuery.value = Pair(mSearchViewModel.currentFragmentPos, "beach")
        }
    }


}
