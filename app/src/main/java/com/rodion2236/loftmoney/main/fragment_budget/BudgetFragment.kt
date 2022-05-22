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
import com.rodion2236.loftmoney.adapters.MoneyItemsAdapter
import com.rodion2236.loftmoney.databinding.FragmentBudgetBinding
import com.rodion2236.loftmoney.main.EditModeListener
import com.rodion2236.loftmoney.main.models.LoftmoneyItem

class BudgetFragment : Fragment(), LoftMoneyEditListener {

    private lateinit var adapter: MoneyItemsAdapter
    private var type: String? = null
    private var budgetViewModel = BudgetViewModel()
    private var bindingBudget: FragmentBudgetBinding? = null

    companion object {
        const val TYPE = "typeFragment"
        private const val COLOR_ID = "colorId"

        fun newInstance(colorId: Int, type: String): BudgetFragment {
            val budgetFragment = BudgetFragment()
            val bundle = Bundle()
            bundle.putString(TYPE, type)
            bundle.putInt(COLOR_ID, colorId)
            budgetFragment.arguments = bundle
            return budgetFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        budgetViewModel = ViewModelProvider(this).get(BudgetViewModel::class.java)
        if (arguments != null) {
            type = requireArguments().getString(TYPE)
            adapter = MoneyItemsAdapter(requireArguments().getInt(COLOR_ID))
        } else {
            adapter = MoneyItemsAdapter(R.color.grey)
        }
        adapter.setMoneyCellAdapterClick(object : LoftMoneyItemClickAdapter {
            override fun CellClick(loftmoneyItem: LoftmoneyItem?) {
                if (budgetViewModel.isEditMode.value!!) {
                    loftmoneyItem!!.isSelected = loftmoneyItem.isSelected
                    adapter.updateItem(loftmoneyItem)
                    checkSelectedItem()
                }
            }

            override fun LongCellClick(loftmoneyItem: LoftmoneyItem?) {
                if (budgetViewModel.isEditMode.value!!) {
                    loftmoneyItem!!.isSelected = true
                    adapter.updateItem(loftmoneyItem)
                    budgetViewModel.setEditMode(true)
                    checkSelectedItem()
                }
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingBudget = FragmentBudgetBinding.inflate(inflater, container, false)
        return bindingBudget!!.root
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

    private fun init() {
            bindingBudget!!.loftRecycler.layoutManager = LinearLayoutManager(activity)
            bindingBudget!!.loftRecycler.adapter = adapter
            bindingBudget!!.loftRecycler.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
    }


    override fun onResume() {
        super.onResume()
        loadItems()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindingBudget = null
    }

    private fun loadItems() {
        budgetViewModel.loadIncomes(
            (requireActivity().application as LoftApp).moneyApi,
            requireActivity().getSharedPreferences(getString(R.string.app_name), 0),
            type
        )
    }

    private fun configureViewModel() {
        budgetViewModel.selectedCounter.observe(viewLifecycleOwner) { newCount: Int? ->
            if (activity is EditModeListener) {
                (activity as EditModeListener).onCounterChanged(newCount!!)
            }
        }

        budgetViewModel.isEditMode.observe(viewLifecycleOwner) { isEditMode: Boolean? ->
            if (activity is EditModeListener) {
                (activity as EditModeListener).onEditModeChanged(isEditMode!!)
            }
        }

        budgetViewModel.moneyItemsList.observe(viewLifecycleOwner) { loftmoneyItems: List<LoftmoneyItem> ->
            adapter.addLoftItem(loftmoneyItems)
        }

        budgetViewModel.isRefreshing.observe(viewLifecycleOwner) { isRefreshing: Boolean? ->
            bindingBudget?.swipeRefresh!!.isRefreshing = isRefreshing!!
        }

        budgetViewModel.removeItemDoneSuccess.observe(viewLifecycleOwner) { success: Boolean ->
            if (success) {
                loadItems()
            }
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

    private fun checkSelectedItem() {
        var selectedItemsCount = 0
        for (loftmoneyItem in adapter.loftmoneyItemList) {
            if (loftmoneyItem.isSelected) {
                selectedItemsCount++
            }
        }
        budgetViewModel.setSelectedItemsCount(selectedItemsCount)
    }

    override fun onClearEdit() {
        budgetViewModel.setEditMode(false)
        budgetViewModel.resetSelectedCounter()
        for (moneyItem in adapter.loftmoneyItemList) {
            if (moneyItem.isSelected) {
                moneyItem.isSelected = false
                adapter.updateItem(moneyItem)
            }
        }
    }

    override fun onClearSelectedClick() {
        budgetViewModel.setEditMode(false)
        budgetViewModel.resetSelectedCounter()
        budgetViewModel.removeItem(
            (requireActivity().application as LoftApp).moneyApi,
            requireActivity().getSharedPreferences(getString(R.string.app_name), 0),
            adapter.loftmoneyItemList
        )
    }
}


