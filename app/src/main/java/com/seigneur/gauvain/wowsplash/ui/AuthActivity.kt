package com.seigneur.gauvain.wowsplash.ui

import android.graphics.Bitmap
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import com.seigneur.gauvain.wowsplash.R
import com.seigneur.gauvain.wowsplash.data.api.AUTH_INITIAL_URI
import com.seigneur.gauvain.wowsplash.data.repository.PhotoRepository
import com.seigneur.gauvain.wowsplash.ui.home.HomeViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_auth.*
import org.koin.android.ext.android.inject
import timber.log.Timber


class AuthActivity : AppCompatActivity() {

    val mPhotoRepository by inject<PhotoRepository>()
    val mCompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        
        progressBar.max = 100
        webView.loadUrl(AUTH_INITIAL_URI)
        webView.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView, webRessource: WebResourceRequest): Boolean {
                Timber.d("lol it works! ${webRessource.url}")
                if (webRessource.url.toString().startsWith("https://wowsplah/authentication/callback")) {
                    val uri = webRessource.url
                    val keyCode = uri.getQueryParameter(KEY_CODE)
                    Timber.d("wesh $keyCode")

                    mCompositeDisposable.add(
                        mPhotoRepository.getAccessToken(keyCode)
                            .subscribeBy(
                                onSuccess = {
                                    Timber.d("accesstoken rceived $it")
                                    //mListResult.value = ListResult(inList = it)
                                },
                                onError = {
                                    Timber.d("onError $it")
                                    //mListResult.value = ListResult(inError = it)
                                }
                            )
                    )
                    //val resultIntent = Intent()
                    //resultIntent.putExtra(KEY_CODE, uri.getQueryParameter(KEY_CODE))
                    //setResult(Activity.RESULT_OK, resultIntent)
                    //finish()
                }

                return super.shouldOverrideUrlLoading(view, webRessource)
            }

            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                progressBar.visibility = View.VISIBLE
                progressBar.progress = 0
            }

            override fun onPageFinished(view: WebView, url: String) {
                progressBar.visibility = View.GONE
            }
        }

        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                progressBar.progress = newProgress
            }

            override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {

                Timber.d("wesh morray $consoleMessage")
                return super.onConsoleMessage(consoleMessage)
            }
        }

        val url = intent.getStringExtra(KEY_URL)
        webView.loadUrl(url)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {

        val KEY_URL = "url"
        val KEY_CODE = "code"
    }


}