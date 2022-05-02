package com.rodion2236.loftmoney

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager

import com.rodion2236.loftmoney.databinding.FragmentBudgetBinding
import java.util.ArrayList

class BudgetFragment : Fragment() {

    private val rvadapter = LoftRVAdapter()
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
            loftRecycler.adapter = rvadapter

            loftRecycler.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }
        bindingBudget?.addButtonBudget?.setOnClickListener(View.OnClickListener {
            startActivityForResult(Intent(activity, AdditemActivity::class.java), 100)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val loftmoneyItem = ArrayList<LoftmoneyItem>()

        if (requestCode == 100 && resultCode == AppCompatActivity.RESULT_OK && data != null) {
            rvadapter.addLoftItem(loftmoneyItem)
        } else if (resultCode == Activity.RESULT_CANCELED) {
        }
    }
}
