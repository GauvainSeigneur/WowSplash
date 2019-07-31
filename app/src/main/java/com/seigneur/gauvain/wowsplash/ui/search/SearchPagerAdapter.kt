package com.seigneur.gauvain.wowsplash.ui.search

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.seigneur.gauvain.wowsplash.ui.search.collection.SearchCollectionFragment
import com.seigneur.gauvain.wowsplash.ui.search.photo.SearchPhotoFragment

class SearchPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                SearchPhotoFragment()
            }
            else -> {
                return SearchCollectionFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Photo"
            else -> {
                return "Collection"
            }
        }
    }
}
