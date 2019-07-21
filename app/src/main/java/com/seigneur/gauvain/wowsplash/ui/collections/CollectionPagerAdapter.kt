package com.seigneur.gauvain.wowsplash.ui.collections

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class CollectionPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                CollectionListFragment(null)
            }
            else -> {
                return CollectionListFragment("featured")
            }
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "All"
            else -> {
                return "Featured"
            }
        }
    }
}
