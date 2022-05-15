package com.rodion2236.loftmoney.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.rodion2236.loftmoney.main.fragment_budget.BudgetFragment
import com.rodion2236.loftmoney.R
import com.rodion2236.loftmoney.databinding.ActivityMainBinding
import com.rodion2236.loftmoney.main.fragment_budget.LoftMoneyEditListener
import com.rodion2236.loftmoney.second.AdditemActivity

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private var currentPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configureActionMode()

        binding.viewpager.adapter = ViewPagerFragmentAdapter(this)
        binding.viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                currentPosition = position
            }
        })

        val viewpagerListTitle = arrayOf(
            getString(R.string.incomes),
            getString(R.string.expenses)
        )

        TabLayoutMediator(binding.tabLayout, binding.viewpager) {
            tab, pos -> tab.text = viewpagerListTitle[pos]
        }.attach()

        val intent = Intent(this, AdditemActivity::class.java)
        binding.addFloatingButtonBudget.setOnClickListener(View.OnClickListener {
                var type = "0"
                if (currentPosition == 0) {
                    type = "income"
                } else if (currentPosition == 1) {
                    type = "expense"
//                } else if (currentPosition == 2) {
//                    type =
                }
                intent.putExtra(BudgetFragment.TYPE, type)
                startActivity(intent)
        })
    }

    private fun  configureActionMode() {
        binding.addItemButtonBack.setOnClickListener(View.OnClickListener { closeEditMode() })
        binding.deleteItemButton.setOnClickListener(View.OnClickListener {
            AlertDialog.Builder(this@MainActivity)
                .setTitle(getString(R.string.delete_items_title))
                .setMessage(getString(R.string.delete_items_message))
                .setNegativeButton(R.string.action_mode_no) { dialogInterface, i -> }
                .setPositiveButton(R.string.action_mode_yes) { dialogInterface, i -> clearSelectedItems() }
                .show()
        })
    }

    private fun closeEditMode() {
        val fragment = supportFragmentManager.fragments[binding.viewpager.currentItem]
        if (fragment is LoftMoneyEditListener) {
            (fragment as LoftMoneyEditListener).onClearEdit()
        }
    }

    private fun clearSelectedItems() {
        val fragment = supportFragmentManager.fragments[binding.viewpager.currentItem]
        if (fragment is LoftMoneyEditListener) {
            (fragment as LoftMoneyEditListener).onClearSelectedClick()
        }
    }

    override fun onEditModeChanged(status: Boolean) {
        if (status) {
            binding.addFloatingButtonBudget.hide()
        } else {
            binding.addFloatingButtonBudget.show()
        }
        binding.toolbar.setBackgroundColor(
            ContextCompat.getColor(this,
                if (status) R.color.primary_color_selected_items
                else R.color.primary_color
            )
        )
        binding.deleteItemButton.visibility = if (status) View.VISIBLE else View.GONE
        binding.deleteItemButton.visibility = if (status) View.VISIBLE else View.GONE
        binding.tabLayout.setBackgroundColor(
            ContextCompat.getColor(this,
                if (status) R.color.primary_color_selected_items
                else R.color.primary_color
            )
        )
        val window = window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this,
            if (status) R.color.primary_color_selected_items
            else R.color.primary_color
        )
    }

    override fun onCounterChanged(newCount: Int) {
        if (newCount >= 0) {
            binding.titleToolbar.text = getString(R.string.selected, newCount)
        } else {
            binding.titleToolbar.text = getString(R.string.budget_accounting)
        }
    }

    private inner class ViewPagerFragmentAdapter
        (fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
        val currentFragment: Fragment? = null

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> BudgetFragment.newInstance(getString(R.string.incomes))
                1 -> BudgetFragment.newInstance(getString(R.string.expenses))
//              2 -> BudgetFragment.newInstance(getString(R.string.nextFragment)
                else -> error("error") //временно
            }
        }

        override fun getItemCount(): Int {
            return 2
        }
    }
}

