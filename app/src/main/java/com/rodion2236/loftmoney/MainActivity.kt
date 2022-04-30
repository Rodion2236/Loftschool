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
    private val rvadapter = LoftRVAdapter()

    private val viewpagerList = listOf(
        Vp1Fragment(),
        Vp2Fragment()
    )

    private val viewpagerListTitle = listOf(
        "Доходы",
        "Расходы"
    )

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

    fun newObjectAdd(view: View) {
        val i = Intent(this, AdditemActivity::class.java)
        i.putExtra("key", "")
        startActivityForResult(i, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val loftmoneyItem = ArrayList<LoftmoneyItem>()

        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            rvadapter.addLoftItem(loftmoneyItem)
        } else if (resultCode == Activity.RESULT_CANCELED) {

        }
    }
}