package com.seigneur.gauvain.wowsplash.ui.collections


import android.os.Bundle
import android.view.View
import com.seigneur.gauvain.wowsplash.R
import com.seigneur.gauvain.wowsplash.ui.base.BaseFragment
import kotlinx.android.synthetic.main.layout_tabbed_list.*

class CollectionFragment : BaseFragment() {

    override val fragmentLayout: Int
        get() = R.layout.layout_tabbed_list

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentAdapter = CollectionPagerAdapter(childFragmentManager)
        viewPager.adapter = fragmentAdapter
        mTabs.visibility = View.GONE
       // mTabs.setupWithViewPager(viewPager)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }




}
