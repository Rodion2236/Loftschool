package com.rodion2236.loftmoney.main.models

import com.rodion2236.loftmoney.remote.moneyApi.MoneyItemsResponse

data class LoftmoneyItem(var title:String, var cost: Double, var id: Int) {

    var isSelected = false

    companion object {
        fun getInstance(moneyRemoteItem: MoneyItemsResponse): LoftmoneyItem {
            return LoftmoneyItem(moneyRemoteItem.name, moneyRemoteItem.price, moneyRemoteItem.id)
        }
    }
}