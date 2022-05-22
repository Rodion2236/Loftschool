package com.rodion2236.loftmoney.remote.moneyApi

import com.google.gson.annotations.SerializedName

data class MoneyRemoteItem(
    @SerializedName("id")
    var id: Int,

    @SerializedName("name")
    var name: String,

    @SerializedName("price")
    var price: Double,

    @SerializedName("type")
    var type: String,

    @SerializedName("date")
    val date: String
)
