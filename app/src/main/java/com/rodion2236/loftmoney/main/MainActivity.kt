package com.rodion2236.loftmoney.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.rodion2236.loftmoney.main.fragment_budget.BudgetFragment
import com.rodion2236.loftmoney.R
import com.rodion2236.loftmoney.databinding.ActivityMainBinding
import com.rodion2236.loftmoney.second.AdditemActivity

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private var currentPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewpagerListTitle = arrayOf(
            getString(R.string.incomes),
            getString(R.string.expenses),
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
                }
                intent.putExtra(BudgetFragment.TYPE, type)
                startActivity(intent)
        })

        supportFragmentManager.beginTransaction()
            .replace(R.id.loftRecycler, BudgetFragment())
            .commit()
    }

    private inner class ViewPagerFragmentAdapter
        (fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
        val currentFragment: Fragment? = null

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> BudgetFragment.newInstance(getString(R.string.incomes))
                1 -> BudgetFragment.newInstance(getString(R.string.expenses))
                else -> error("error") //временно
            }
        }

        override fun getItemCount(): Int {
            return 2
        }
    }
}

