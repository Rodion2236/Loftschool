package com.rodion2236.loftmoney.api

import com.google.gson.annotations.SerializedName

data class MoneyRemoteItem(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("price")
    val price: Double,

    @SerializedName("type")
    val type: String,

    @SerializedName("date")
    val date: String
)
