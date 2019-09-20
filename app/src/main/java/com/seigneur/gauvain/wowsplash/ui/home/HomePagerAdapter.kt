package com.seigneur.gauvain.wowsplash.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.seigneur.gauvain.wowsplash.ui.photo.PhotoListFragment

class HomePagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                PhotoListFragment.newInstance("latest")
            }
            else -> {
                return PhotoListFragment.newInstance("popular")
            }
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Latest"
            else -> {
                return "Popular"
            }
        }
    }
}
