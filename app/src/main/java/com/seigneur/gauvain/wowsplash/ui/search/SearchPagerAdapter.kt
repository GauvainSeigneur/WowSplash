package com.seigneur.gauvain.wowsplash.ui.search

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.seigneur.gauvain.wowsplash.ui.search.collection.SearchCollectionFragment
import com.seigneur.gauvain.wowsplash.ui.search.photo.SearchPhotoFragment
import com.seigneur.gauvain.wowsplash.ui.search.user.SearchUserFragment

class SearchPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                SearchPhotoFragment()
            }
            1 -> {
                SearchCollectionFragment()
            }
            else -> {
                return SearchUserFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Photo"
            1 -> "Collection"
            else -> {
                return "User"
            }
        }
    }
}
