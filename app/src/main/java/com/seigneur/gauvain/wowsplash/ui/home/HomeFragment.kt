package com.seigneur.gauvain.wowsplash.ui.home


import android.os.Bundle
import android.view.View
import com.seigneur.gauvain.wowsplash.R
import com.seigneur.gauvain.wowsplash.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment() {

    override val fragmentLayout: Int
        get() = R.layout.fragment_home

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentAdapter = PhotoPagerAdapter(childFragmentManager)
        viewPager.adapter = fragmentAdapter
        mTabs.setupWithViewPager(viewPager)

    }
}
