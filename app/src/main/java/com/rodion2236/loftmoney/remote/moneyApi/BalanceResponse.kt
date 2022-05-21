package com.rodion2236.loftmoney.remote.moneyApi

import com.google.gson.annotations.SerializedName

class BalanceResponse {
    val status: String? = null

    @SerializedName("total_expenses")
    val allExpenses: Float? = null

    @SerializedName("total_income")
    val allIncomes: Float? = null
}