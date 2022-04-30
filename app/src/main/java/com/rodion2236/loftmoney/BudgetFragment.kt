package com.rodion2236.loftmoney

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager

import com.rodion2236.loftmoney.databinding.FragmentBudgetBinding

class BudgetFragment : Fragment() {

    private val adapter = LoftRVAdapter()
    private var bindingBudget: FragmentBudgetBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        bindingBudget = FragmentBudgetBinding.inflate(inflater, container, false)
        val view = bindingBudget!!.root
        return view

        init()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        bindingBudget = null
    }

    private fun init() {

        bindingBudget?.apply {
            loftRecycler.layoutManager = LinearLayoutManager(activity)
            loftRecycler.adapter = adapter

            loftRecycler.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }
    }
}
