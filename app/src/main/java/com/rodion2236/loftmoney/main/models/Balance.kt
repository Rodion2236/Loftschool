package com.rodion2236.loftmoney.main.models

import com.rodion2236.loftmoney.remote.moneyApi.BalanceResponse

class Balance(val allExpenses: Int, val allIncomes: Int, val inStock: Int) {

    companion object {
        fun getInstance(balanceResponse: BalanceResponse): Balance {
            val expenses = Math.round(balanceResponse.allExpenses!!)
            val incomes = Math.round(balanceResponse.allIncomes!!)
            val inStock = incomes - expenses
            return Balance(expenses, incomes, inStock)
        }
    }
}