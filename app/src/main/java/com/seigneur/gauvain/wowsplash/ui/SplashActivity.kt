package com.seigneur.gauvain.wowsplash.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.seigneur.gauvain.wowsplash.R
import com.seigneur.gauvain.wowsplash.ui.base.BaseFragment
import com.seigneur.gauvain.wowsplash.ui.collections.CollectionFragment
import com.seigneur.gauvain.wowsplash.ui.home.HomeFragment
import com.seigneur.gauvain.wowsplash.ui.logIn.LogInFragment
import com.seigneur.gauvain.wowsplash.ui.search.SearchFragment
import com.seigneur.gauvain.wowsplash.utils.FragmentStateManager
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : AppCompatActivity() {

    private val mSplashViewModel by viewModel<SplashViewModel>()

    /**
     * From AppCompatActivity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        mSplashViewModel.fetchTokenFromDB()

        mSplashViewModel.lol.observe(
            this, Observer<String> {
                if (it.equals("lol")) {
                    val goIntent = Intent(this, MainActivity::class.java)
                    startActivity(goIntent)
                }
            })

    }

}
