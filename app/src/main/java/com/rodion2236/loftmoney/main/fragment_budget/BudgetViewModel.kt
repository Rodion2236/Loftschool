package com.rodion2236.loftmoney.main.fragment_budget

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rodion2236.loftmoney.LoftApp
import com.rodion2236.loftmoney.main.models.LoftmoneyItem
import com.rodion2236.loftmoney.remote.moneyApi.MoneyApi
import com.rodion2236.loftmoney.remote.moneyApi.MoneyItemsResponse
import com.rodion2236.loftmoney.remote.moneyApi.MoneyRemoteItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.ArrayList

class BudgetViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _moneyItemsList = MutableLiveData<List<LoftmoneyItem>>()
    var moneyItemsList: LiveData<List<LoftmoneyItem>> = _moneyItemsList

    private val _messageString = MutableLiveData("")
    var messageString: LiveData<String> = _messageString

    private val _messageInt = MutableLiveData(-1)
    var messageInt: LiveData<Int> = _messageInt

    private val _isRefreshing = MutableLiveData(false)
    var isRefreshing: LiveData<Boolean> = _isRefreshing

    private val _isEditMode = MutableLiveData(false)
    var isEditMode: LiveData<Boolean> = _isEditMode

    private val _selectedCounter = MutableLiveData(-1)
    var selectedCounter: LiveData<Int> = _selectedCounter

    private val _removeItemDoneSuccess = MutableLiveData(false)
    var removeItemDoneSuccess: LiveData<Boolean> = _removeItemDoneSuccess

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    fun setEditMode(isEditMode: Boolean) {
        _isEditMode.postValue(isEditMode)
    }

    fun setSelectedItemsCount(selectedItemsCount: Int) {
        _selectedCounter.postValue(selectedItemsCount)
    }

    fun resetSelectedCounter() {
        _selectedCounter.postValue(-1)
    }

    fun removeItem(
        moneyApi: MoneyApi,
        sharedPreferences: SharedPreferences,
        moneyItemList: List<LoftmoneyItem>
    ) {
        val authToken = sharedPreferences.getString(LoftApp.AUTH_KEY, "")
        for (moneyItem in moneyItemList) {
            if (moneyItem.isSelected) {
                moneyApi.remove(moneyItem.id, authToken)
                    ?.subscribeOn(Schedulers.io())
                    ?.observeOn(AndroidSchedulers.mainThread())?.let {
                        compositeDisposable.add(
                            it
                                .subscribe(
                                    { _removeItemDoneSuccess.postValue(true) }
                                ) { error: Throwable ->
                                    _removeItemDoneSuccess.postValue(false)
                                    _messageString.postValue(error.localizedMessage)
                                }
                        )
                    }
            }
        }
    }

    fun loadIncomes(moneyApi: MoneyApi,
                    sharedPreferences: SharedPreferences,
                    type: String?) {
        val authToken = sharedPreferences.getString(LoftApp.AUTH_KEY, "")
        compositeDisposable.add(moneyApi.getmoneyItems(type, authToken)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ moneyRemoteItems: List<MoneyRemoteItem> ->
                val loftmoneyItems: MutableList<LoftmoneyItem> = ArrayList()
                for (moneyRemoteItem in moneyRemoteItems) {
                    loftmoneyItems.add(LoftmoneyItem.getInstance(moneyRemoteItem))
                }
                _moneyItemsList.postValue(loftmoneyItems)
                _isRefreshing.postValue(false)
            }) { throwable: Throwable ->
                _messageString.postValue(throwable.localizedMessage)
                _isRefreshing.postValue(false)
            })
    }
}