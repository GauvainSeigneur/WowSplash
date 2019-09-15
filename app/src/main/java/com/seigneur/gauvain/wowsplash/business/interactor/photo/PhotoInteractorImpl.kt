package com.seigneur.gauvain.wowsplash.business.interactor.photo

import com.seigneur.gauvain.wowsplash.data.repository.PhotoRepository
import com.seigneur.gauvain.wowsplash.ui.photoList.PhotoPresenter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

/**
 *  Must be used only by ViewModel which inject all dependencies
 */
class PhotoInteractorImpl(
    private val compositeDisposable: CompositeDisposable,
    private val photoRepository: PhotoRepository,
    private val photoPresenter: PhotoPresenter
): PhotoInteractor {

    override fun likePhoto(id: String?, pos: Int) {
        id?.let {
            compositeDisposable.add(
                photoRepository.likePhoto(id)
                    .subscribeBy(  // named arguments for lambda Subscribers
                        onSuccess = { photoPresenter.presentPhotoLiked(pos) },
                        onError = { photoPresenter.presentGlobalError() }
                    )
            )
        }?: photoPresenter.presentGlobalError()
    }
}

