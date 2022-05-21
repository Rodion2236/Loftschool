package com.rodion2236.loftmoney.main.models

import com.rodion2236.loftmoney.remote.moneyApi.MoneyRemoteItem

data class LoftmoneyItem(var title:String, var cost: Double, var id: Int) {

    var isSelected = false

    companion object {
        fun getInstance(moneyRemoteItem: MoneyRemoteItem): LoftmoneyItem {
            return LoftmoneyItem(moneyRemoteItem.name, moneyRemoteItem.price, moneyRemoteItem.id)
        }
    }
}