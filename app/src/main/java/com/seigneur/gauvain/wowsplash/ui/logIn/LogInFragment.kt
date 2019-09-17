package com.seigneur.gauvain.wowsplash.ui.logIn

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.*
import androidx.lifecycle.Observer
import com.seigneur.gauvain.wowsplash.R
import com.seigneur.gauvain.wowsplash.data.api.AUTH_INITIAL_URI
import com.seigneur.gauvain.wowsplash.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_log_in.progressBar
import kotlinx.android.synthetic.main.fragment_log_in.webView
import org.koin.androidx.viewmodel.ext.android.viewModel

class LogInFragment : BaseFragment() {

    private val mLogInViewModel by viewModel<LogInViewModel>()

    override val fragmentLayout: Int
        get() = R.layout.fragment_log_in

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar.max = 100
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
    }

    override fun subscribeToLiveData() {
        mLogInViewModel.mWebProgressValue.observe(viewLifecycleOwner,
            Observer {
                progressBar.progress = it
                if (it == 100)
                    progressBar.visibility = View.GONE
            })

        mLogInViewModel.mLoginResult.observe(viewLifecycleOwner, Observer {
            when (it) {
                is LogInResult.LogInSuccess -> {
                    //todo - close fragment or activity
                }
                is LogInResult.LogInError -> {
                    //todo - close fragment or activity
                }
            }
        })

    }

}
