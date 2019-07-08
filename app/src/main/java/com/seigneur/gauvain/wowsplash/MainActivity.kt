package com.seigneur.gauvain.wowsplash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.seigneur.gauvain.wowsplash.data.repository.PhotoRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import org.koin.android.ext.android.inject
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    // Inject
    val mRepository by inject<PhotoRepository>()
    private val mCompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mCompositeDisposable.add(
            mRepository.getPhotos()
            .subscribeBy(
                onNext = { Timber.d("onNext listsize ${it.size} Photos: $it")}, //when all operation finish, notify again to load image
                onError = {Timber.d("onError $it")},
                onComplete = {Timber.d("onComplete")}
            )
        )
    }

}
