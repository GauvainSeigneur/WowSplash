package com.seigneur.gauvain.wowsplash.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.seigneur.gauvain.wowsplash.R
import com.seigneur.gauvain.wowsplash.ui.collections.CollectionFragment
import com.seigneur.gauvain.wowsplash.ui.logIn.LogInActivity
import com.seigneur.gauvain.wowsplash.ui.photo.PhotoListFragment
import com.seigneur.gauvain.wowsplash.ui.postPhoto.PostPhotoFragment
import com.seigneur.gauvain.wowsplash.ui.search.SearchFragment
import com.seigneur.gauvain.wowsplash.ui.user.UserFragment
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

    fun displayRequestLoginSnackBar(){
        Snackbar.make(container, "you have to connected", Snackbar.LENGTH_LONG)
            .setAnchorView(R.id.mBottomNavigation)
            .setAction("Connect", object: View.OnClickListener{
                override fun onClick(p0: View?) {
                    val i = Intent(this@MainActivity, LogInActivity::class.java)
                    startActivity(i)
                }
            })
            .show()
    }


    /**
     * Initialize Fragment manager and default value
     */
    private fun initFragmentManager(savedInstanceState: Bundle?) {
        mFragmentStateManager = object : FragmentStateManager(mfragmentContainer, supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                when (position) {
                    0 -> return PhotoListFragment()
                    1 -> return CollectionFragment()//CollectionFragment()
                    2 -> return PostPhotoFragment()
                    3 -> return SearchFragment()
                    4 -> return UserFragment()
                }
                return PhotoListFragment()
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

}
