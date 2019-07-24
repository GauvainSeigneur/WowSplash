package com.seigneur.gauvain.wowsplash.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.seigneur.gauvain.wowsplash.R
import com.seigneur.gauvain.wowsplash.business.result.AccessTokenResult
import com.seigneur.gauvain.wowsplash.ui.main.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class SplashActivity : AppCompatActivity() {

    private val mSplashViewModel by viewModel<SplashViewModel>()

    /**
     * From AppCompatActivity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        mSplashViewModel.fetchToken()


       mSplashViewModel.mTokenResult.observe(
            this, Observer{
               when(it){
                   is AccessTokenResult.Fetched -> {
                       Timber.d("token is saved and is ${it.token}")
                       val goIntent = Intent(this, MainActivity::class.java)
                       startActivity(goIntent)
                   }
                   is AccessTokenResult.UnFetched -> {
                       val goIntent = Intent(this, MainActivity::class.java)
                       startActivity(goIntent)
                   }
               }
            })

    }

}
