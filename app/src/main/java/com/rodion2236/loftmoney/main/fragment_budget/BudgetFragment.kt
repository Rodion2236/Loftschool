package com.rodion2236.loftmoney.main.fragment_budget

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.rodion2236.loftmoney.second.AdditemActivity
import com.rodion2236.loftmoney.LoftApp
import com.rodion2236.loftmoney.R
import com.rodion2236.loftmoney.adapters.LoftRVAdapter
import com.rodion2236.loftmoney.remote.LoftmoneyItem

import com.rodion2236.loftmoney.databinding.FragmentBudgetBinding

class BudgetFragment : Fragment() {

    private val rvadapter = LoftRVAdapter()
    private var type: String? = null
    private var budgetViewModel = BudgetViewModel()
    private var bindingBudget: FragmentBudgetBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        bindingBudget = FragmentBudgetBinding.inflate(inflater, container, false)
        val view = bindingBudget!!.root
        return view

        init()
        configureViewModel()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        bindingBudget = null
    }

    private fun init() {
        bindingBudget?.apply {
            loftRecycler.layoutManager = LinearLayoutManager(activity)
            loftRecycler.adapter = rvadapter

            loftRecycler.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }
        bindingBudget?.addButtonBudget?.setOnClickListener(View.OnClickListener {
            startActivity(Intent(activity, AdditemActivity::class.java))
        })
    }

    override fun onResume() {
        super.onResume()
        loadItems()
    }

    private fun loadItems() {
        budgetViewModel.loadIncomes(
            (requireActivity().application as LoftApp).moneyApi,
            requireActivity().getSharedPreferences(getString(R.string.app_name), 0),
            type
        )
    }

    private fun configureViewModel() {
        budgetViewModel = ViewModelProvider(this).get(BudgetViewModel::class.java)
        budgetViewModel.moneyItemsList.observe(viewLifecycleOwner) { moneyItems: List<LoftmoneyItem> ->
            rvadapter.addLoftItem(moneyItems)
        }

        budgetViewModel.messageString.observe(viewLifecycleOwner) { message: String ->
            if (message != "") {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        }

        budgetViewModel.messageInt.observe(viewLifecycleOwner) { message: Int ->
            if (message > 0) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        const val TYPE = "typeFragment"

        fun newInstance(type: String?): BudgetFragment {
            val budgetFragment = BudgetFragment()
            val bundle = Bundle()
            bundle.putString(TYPE, type)
            budgetFragment.arguments = bundle
            return budgetFragment
        }
    }
}

