package com.rodion2236.loftmoney

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayoutMediator
import com.rodion2236.loftmoney.databinding.ActivityMainBinding
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val vpadapter = VpAdapter(this, viewpagerList)
        binding.viewpager.adapter = vpadapter

        TabLayoutMediator(binding.tabLayout, binding.viewpager) {
            tab, pos -> tab.text = viewpagerListTitle[pos]
        }.attach()

        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_container, BudgetFragment())
            .commit()
    }

    private val viewpagerList = listOf(
        BudgetFragment(),
    )

    private val viewpagerListTitle = listOf(
        "Доходы",
        "Расходы"
    )
}

