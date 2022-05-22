package com.rodion2236.loftmoney.second

import io.reactivex.disposables.CompositeDisposable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.rodion2236.loftmoney.remote.moneyApi.MoneyApi
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers

class AddItemViewModel: ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _messageString = MutableLiveData("")
    var messageString: LiveData<String> = _messageString

    private val _messageInt = MutableLiveData(-1)
    var messageInt: LiveData<Int> = _messageInt

    private val _successAddItem = MutableLiveData<Boolean>()
    var successAddItem: LiveData<Boolean> = _successAddItem

    fun addItemStream(moneyApi: MoneyApi, name: String?, price: Int, type: String?, token: String?) {
        moneyApi.addItem(price, name, type, token)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())?.let {
                compositeDisposable.add(
                it
                    .subscribe(
                        { _successAddItem.postValue(true) }
                    ) { error: Throwable ->
                        _successAddItem.postValue(false)
                        _messageString.postValue(error.localizedMessage)
                    })
            }
    }
}