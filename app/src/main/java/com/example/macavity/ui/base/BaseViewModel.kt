package com.example.macavity.ui.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


open class BaseViewModel @Inject constructor( ) : ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}