package com.rodion2236.loftmoney.api

import com.google.gson.annotations.SerializedName

class MoneyItemsResponse (
    @SerializedName("status")
    val status: String,

    @SerializedName("data")
    val data: List<MoneyRemoteItem>
    )