package com.rodion2236.loftmoney.main.fragment_budget

import com.rodion2236.loftmoney.main.models.LoftmoneyItem

interface LoftMoneyItemClickAdapter {
    fun LongCellClick(loftmoneyItem: LoftmoneyItem?)
    fun CellClick(loftmoneyItem: LoftmoneyItem?)
}