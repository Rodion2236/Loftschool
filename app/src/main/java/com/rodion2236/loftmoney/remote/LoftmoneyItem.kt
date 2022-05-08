package com.rodion2236.loftmoney.remote

import com.rodion2236.loftmoney.api.MoneyItemsResponse

data class LoftmoneyItem(var title:String, var cost: Double) {

    companion object {
        fun getInstance(moneyRemoteItem: MoneyItemsResponse): LoftmoneyItem {
            return LoftmoneyItem(moneyRemoteItem.name, moneyRemoteItem.price)
        }
    }
}