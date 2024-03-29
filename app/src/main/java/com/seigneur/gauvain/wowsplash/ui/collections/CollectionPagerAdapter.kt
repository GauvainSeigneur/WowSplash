package com.seigneur.gauvain.wowsplash.ui.collections

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.seigneur.gauvain.wowsplash.utils.COLLECTION_LIST_ALL
import com.seigneur.gauvain.wowsplash.utils.COLLECTION_LIST_FEATURED

class CollectionPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                CollectionListFragment.newInstance(COLLECTION_LIST_ALL)
            }
            else -> {
                return CollectionListFragment.newInstance(COLLECTION_LIST_FEATURED)
            }
        }
    }

    override fun getCount(): Int {
        return 1
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
