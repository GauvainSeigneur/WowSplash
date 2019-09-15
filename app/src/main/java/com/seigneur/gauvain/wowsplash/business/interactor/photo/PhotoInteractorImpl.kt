package com.seigneur.gauvain.wowsplash.business.interactor.photo

import com.seigneur.gauvain.wowsplash.data.TemporaryDataProvider
import com.seigneur.gauvain.wowsplash.data.model.photo.Photo
import com.seigneur.gauvain.wowsplash.data.repository.PhotoRepository
import com.seigneur.gauvain.wowsplash.di.PHOTO_DETAILS_TEMP_SCOPE_NAME
import com.seigneur.gauvain.wowsplash.di.PHOTO_DETAILS_TEMP_SCOPE_SESSION_ID
import com.seigneur.gauvain.wowsplash.ui.photoList.PhotoPresenter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import org.koin.core.KoinComponent
import org.koin.core.qualifier.named

/**
 *  Must be used only by ViewModel which inject all dependencies
 */
class PhotoInteractorImpl(
    private val compositeDisposable: CompositeDisposable,
    private val photoRepository: PhotoRepository
): PhotoInteractor, KoinComponent {

    private lateinit var presenter:PhotoPresenter

    override fun setUpPresenter(photoPresenter: PhotoPresenter) {
        presenter = photoPresenter
    }

    override fun likePhoto(id: String?, pos: Int) {
        id?.let {
            compositeDisposable.add(
                photoRepository.likePhoto(id)
                    .subscribeBy(  // named arguments for lambda Subscribers
                        onSuccess = { presenter.presentPhotoLiked(pos) },
                        onError = { presenter.presentGlobalError() }
                    )
            )
        }?: presenter.presentGlobalError()
    }

    override fun closeObservable() {
        compositeDisposable.clear()
    }

    override fun onPhotoClicked(photo: Photo?) {
        photo?.let {
            val temporaryDataProviderSession = getKoin().getOrCreateScope(PHOTO_DETAILS_TEMP_SCOPE_SESSION_ID, named(PHOTO_DETAILS_TEMP_SCOPE_NAME))
            val temporaryDataProvider = temporaryDataProviderSession.get<TemporaryDataProvider>()
            temporaryDataProvider.photoClicked.postValue(photo)
        }
        presenter.presentPhotoDetails()
    }

}

