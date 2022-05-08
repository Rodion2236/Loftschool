package com.rodion2236.loftmoney.main.fragment_budget

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rodion2236.loftmoney.LoftApp
import com.rodion2236.loftmoney.remote.LoftmoneyItem
import com.rodion2236.loftmoney.api.MoneyApi
import com.rodion2236.loftmoney.api.MoneyItemsResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.ArrayList

class BudgetViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    var moneyItemsList = MutableLiveData<List<LoftmoneyItem>>()
    var messageString = MutableLiveData("")
    var messageInt = MutableLiveData(-1)

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    fun loadIncomes(moneyApi: MoneyApi,
                    sharedPreferences: SharedPreferences,
                    type: String?) {

        val authToken = sharedPreferences.getString(LoftApp.AUTH_KEY, "")

        moneyApi.getmoneyItems(type, authToken)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())?.let {
                compositeDisposable.add(
                    it
                .subscribe({ moneyRemoteItems: List<MoneyItemsResponse?>? ->
                    val moneyItems: MutableList<LoftmoneyItem> = ArrayList()
                    for (moneyRemoteItem in moneyRemoteItems!!) {
                        moneyItems.add(LoftmoneyItem.getInstance(moneyRemoteItems))
                    }
                    moneyItemsList.postValue(moneyItems)
                }) { throwable: Throwable -> messageString.postValue(throwable.localizedMessage) })
            }
    }
}