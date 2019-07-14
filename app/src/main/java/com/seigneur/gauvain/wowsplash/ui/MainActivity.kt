package com.seigneur.gauvain.wowsplash.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import com.google.android.material.bottomappbar.BottomAppBar
import com.seigneur.gauvain.wowsplash.R
import com.seigneur.gauvain.wowsplash.ui.base.BaseFragment
import com.seigneur.gauvain.wowsplash.ui.collections.CollectionsFragment
import com.seigneur.gauvain.wowsplash.ui.home.HomeFragment
import com.seigneur.gauvain.wowsplash.ui.logIn.LogInFragment
import com.seigneur.gauvain.wowsplash.ui.logIn.LogInViewModel
import com.seigneur.gauvain.wowsplash.ui.search.SearchFragment
import com.seigneur.gauvain.wowsplash.utils.FragmentStateManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var mFragmentStateManager: FragmentStateManager? = null

    /**
     * From AppCompatActivity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.mipmap.ic_launcher)
        }

        initFragmentManager(savedInstanceState)
        mBottomNavigation.setOnNavigationItemSelectedListener { item ->
            showFragment(  getNavPositionFromMenuItem(item))
            true
        }

        mBottomNavigation.setOnNavigationItemReselectedListener { _ ->
        }
    }


    /**
     * Initialize Fragment manager and default value
     */
    private fun initFragmentManager(savedInstanceState: Bundle?) {
        mFragmentStateManager = object : FragmentStateManager(mfragmentContainer, supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                when (position) {
                    0 -> return HomeFragment()
                    1 -> return CollectionsFragment()
                    2 -> return SearchFragment()
                    3 -> return SearchFragment()
                    4 -> return SearchFragment()
                }
                return HomeFragment()
            }
        }
        if (savedInstanceState == null) {
            mFragmentStateManager?.changeFragment(0)
        }
    }

    /**
     * get position form item id in bottom navigation menu
     */
    private fun getNavPositionFromMenuItem(menuItem: MenuItem): Int {
        return when (menuItem.itemId) {
            R.id.navigation_home                ->  0
            R.id.navigation_collections         ->  1
            R.id.navigation_add                 ->  2
            R.id.navigation_search              ->  3
            R.id.navigation_info                ->  4
            else                                ->  0
        }
    }

    /**
     * Show a fragment thanks to mFragmentStateManager
     */
    private fun showFragment(pos: Int) {
        mFragmentStateManager?.changeFragment(pos)
    }

    private fun getPostFragment(): BaseFragment {
        //TOdo - listen access room livedata to check if access token exists,
        //if not display logInFragment
        val accessToken:String?=null //data to be observed
        accessToken?.let {
            return SearchFragment()
        }
        return LogInFragment()

    }
}
