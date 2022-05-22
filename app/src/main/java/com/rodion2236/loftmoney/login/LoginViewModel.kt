package com.rodion2236.loftmoney.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rodion2236.loftmoney.remote.authApi.AuthApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class LoginViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    var messageString = MutableLiveData("")
    var authToken = MutableLiveData("")

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    fun makeLogin(authApi: AuthApi, userId: String?) {
        compositeDisposable.add(authApi.makeLogin(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { auth ->
                    authToken.postValue(auth.authToken)
                },
                { throwable: Throwable ->
                    messageString.postValue(throwable.localizedMessage)
                }
            )
        )
    }
}