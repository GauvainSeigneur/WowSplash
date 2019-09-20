package com.seigneur.gauvain.wowsplash.ui.logIn

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.Observer
import com.seigneur.gauvain.wowsplash.R
import com.seigneur.gauvain.wowsplash.data.api.AUTH_INITIAL_URI
import kotlinx.android.synthetic.main.fragment_log_in.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LogInActivity : AppCompatActivity() {

    private val mLogInViewModel by viewModel<LogInViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_log_in)
        webView.loadUrl(AUTH_INITIAL_URI)
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, webRessource: WebResourceRequest): Boolean {
                mLogInViewModel.checkAuthUrl(webRessource.url)
                return super.shouldOverrideUrlLoading(view, webRessource)
            }

            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                mLogInViewModel.mWebProgressValue.value = 0
            }
        }

        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                mLogInViewModel.mWebProgressValue.value = newProgress
            }
        }
        subscribeToLiveData()
    }

    fun subscribeToLiveData() {
        mLogInViewModel.mWebProgressValue.observe(this,
            Observer {
                progressBar.progress = it
                if (it == 100)
                    progressBar.visibility = View.GONE
            })

        mLogInViewModel.mLoginResult.observe(this, Observer {
            when (it) {
                is LogInResult.LogInSuccess -> {
                    finish()
                }
                is LogInResult.LogInError -> {
                    //todo - close fragment or activity
                }
            }
        })

    }

}
