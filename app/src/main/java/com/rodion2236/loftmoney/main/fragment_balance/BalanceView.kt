package com.rodion2236.loftmoney.main.fragment_balance

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.core.content.ContextCompat
import com.rodion2236.loftmoney.R

class BalanceView : View {
    private var expenses = 1
    private val expensePaint = Paint()
    private var incomes = 1
    private val incomePaint = Paint()

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init()
    }

    fun update(expenses: Int, incomes: Int) {
        this.expenses = expenses
        this.incomes = incomes
        invalidate()
    }

    private fun init() {
        expensePaint.color = ContextCompat.getColor(context, R.color.primary_color)
        incomePaint.color = ContextCompat.getColor(context, R.color.accent_color)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val total = expenses + incomes
        val expenseAngle = 360f * expenses / total
        val incomesAngle = 360f * incomes / total
        val space = 5
        val px = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            space.toFloat(),
            resources.displayMetrics
        ).toInt()
        val size = Math.min(width, height) - px * 2
        val xMargin = (width - size) / 2
        val yMargin = (height - size) / 2
        canvas.drawArc(
            (xMargin - px).toFloat(), yMargin.toFloat(), (
                    width - xMargin - px).toFloat(), (
                    height - yMargin).toFloat(), 180 - expenseAngle / 2,
            expenseAngle, true, expensePaint
        )
        canvas.drawArc(
            (xMargin + px).toFloat(), yMargin.toFloat(), (
                    width - xMargin + px).toFloat(), (
                    height - yMargin).toFloat(), 360 - incomesAngle / 2,
            incomesAngle, true, incomePaint
        )
    }
}