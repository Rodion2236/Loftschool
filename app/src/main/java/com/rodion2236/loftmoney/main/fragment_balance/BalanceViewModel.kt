package com.rodion2236.loftmoney.main.fragment_balance

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rodion2236.loftmoney.LoftApp
import com.rodion2236.loftmoney.main.models.Balance
import com.rodion2236.loftmoney.main.models.Balance.Companion.getInstance
import com.rodion2236.loftmoney.remote.moneyApi.BalanceResponse
import com.rodion2236.loftmoney.remote.moneyApi.MoneyApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class BalanceViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _messageString = MutableLiveData("")
    var messageString: LiveData<String> = _messageString

    private val _messageInt = MutableLiveData(-1)
    var messageInt: LiveData<Int> = _messageInt

    private val _balance = MutableLiveData<Balance>()
    var balance: LiveData<Balance> = _balance

    fun loadBalance(moneyApi: MoneyApi, sharedPreferences: SharedPreferences) {
        val authToken = sharedPreferences.getString(LoftApp.AUTH_KEY, "")
        compositeDisposable.add(moneyApi.getBalance(authToken)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { balanceResponse ->
                    if (balanceResponse.status != "success")
                        _balance.postValue(getInstance(balanceResponse!!))
                },
                { throwable: Throwable ->
                    _messageString.postValue(throwable.localizedMessage)
                }
            )
        )
    }
}