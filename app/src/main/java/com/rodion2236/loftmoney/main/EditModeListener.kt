package com.rodion2236.loftmoney.main

interface EditModeListener {
    fun onEditModeChanged(status: Boolean)
    fun onCounterChanged(newCount: Int)
}