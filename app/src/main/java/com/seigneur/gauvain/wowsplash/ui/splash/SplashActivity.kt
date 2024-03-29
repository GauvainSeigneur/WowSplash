package com.seigneur.gauvain.wowsplash.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.seigneur.gauvain.wowsplash.R
import com.seigneur.gauvain.wowsplash.ui.main.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : AppCompatActivity() {

    private val mSplashViewModel by viewModel<SplashViewModel>()

    /**
     * From AppCompatActivity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSplashViewModel.fetchTokenFromLocal()
        listenLiveData()
        setContentView(R.layout.activity_splash)
    }

    private fun listenLiveData() {
        mSplashViewModel.tokenFetch.observe(this, Observer {
            val goIntent = Intent(this, MainActivity::class.java)
            startActivity(goIntent)
            finish()
        })
    }

}
