package com.seigneur.gauvain.wowsplash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.seigneur.gauvain.wowsplash.data.api.UnSplashService
import com.seigneur.gauvain.wowsplash.data.model.Photo
import com.seigneur.gauvain.wowsplash.repository.PhotoRepository
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
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
            testPhoto()
            .subscribe {
                Timber.d("list $it")
            }
        )


    }

    fun testPhoto(): Flowable<List<Photo>> {
        Timber.d("getUserFromAPI called")
        return mRepository.getPhotos()
    }
}
