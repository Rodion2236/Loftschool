package com.rodion2236.loftmoney.main.fragment_balance

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import com.rodion2236.loftmoney.LoftApp
import com.rodion2236.loftmoney.R
import com.rodion2236.loftmoney.databinding.FragmentBalanceBinding
import com.rodion2236.loftmoney.main.fragment_budget.BudgetViewModel
import com.rodion2236.loftmoney.main.models.Balance

class BalanceFragment : Fragment() {

    private var bindingBalance: FragmentBalanceBinding? = null
    private var balanceViewModel = BalanceViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        bindingBalance = FragmentBalanceBinding.inflate(layoutInflater)
        val view = bindingBalance!!.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureViewModel()
    }

    private fun configureViewModel() {
        balanceViewModel = ViewModelProvider(this).get(BalanceViewModel::class.java)
        balanceViewModel.balance.observe(viewLifecycleOwner) { balance: Balance ->
            bindingBalance!!.tvExpensesValue.text = balance.allExpenses.toString()
            bindingBalance!!.tvIncomesValue.text = balance.allIncomes.toString()
            bindingBalance!!.tvBalanceFinanceValue.text = (balance.allIncomes - balance.allExpenses).toString()
            if ((!(!bindingBalance!!.tvExpensesValue.text.toString().isEmpty()
                        || !bindingBalance!!.tvIncomesValue.text.toString().isEmpty()
                        || !bindingBalance!!.tvBalanceFinanceValue.text.toString().isEmpty()))
            ) {
                bindingBalance!!.balanceView.update(
                    bindingBalance!!.tvExpensesValue.text.toString().toInt(),
                    bindingBalance!!.tvIncomesValue.text.toString().toInt()
                )
            }
        }
        balanceViewModel.messageString.observe(viewLifecycleOwner) { message: String ->
            if (message != "") {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        }
        balanceViewModel.messageInt.observe(viewLifecycleOwner) { message: Int ->
            if (message > 0) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        loadBalance()
    }

    private fun loadBalance() {
        balanceViewModel.loadBalance(
            (requireActivity().application as LoftApp).moneyApi,
            requireActivity().getSharedPreferences(getString(R.string.app_name), 0)
        )
    }

    companion object {
        fun newInstance(): BalanceFragment {
            return BalanceFragment()
        }
    }
}
