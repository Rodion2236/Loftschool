package com.rodion2236.loftmoney.main.fragment_budget

interface LoftMoneyEditListener {
    fun onClearEdit()
    fun onClearSelectedClick()
    fun onEditModeChanged(status: Boolean)
    fun onCounterChanged(newCount: Int)
}