package com.rodion2236.loftmoney.main.fragment_budget

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.rodion2236.loftmoney.LoftApp
import com.rodion2236.loftmoney.R
import com.rodion2236.loftmoney.adapters.LoftRVAdapter

import com.rodion2236.loftmoney.databinding.FragmentBudgetBinding

class BudgetFragment : Fragment() {

    private val adapter = LoftRVAdapter()
    private var type: String? = null
    private var budgetViewModel = BudgetViewModel()
    private var bindingBudget: FragmentBudgetBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        bindingBudget = FragmentBudgetBinding.inflate(inflater, container, false)
        val view = bindingBudget!!.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        configureViewModel()

        val swipeRefreshLayout: SwipeRefreshLayout = bindingBudget!!.swipeRefresh
        bindingBudget?.swipeRefresh?.setOnRefreshListener {
            loadItems()
            swipeRefreshLayout.isRefreshing = false
        }
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
        budgetViewModel.moneyItemsList.observe(viewLifecycleOwner, adapter::addLoftItem)

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

