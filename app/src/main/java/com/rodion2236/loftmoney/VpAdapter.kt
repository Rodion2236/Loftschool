package com.rodion2236.loftmoney

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class VpAdapter(vpAdapter: FragmentActivity,
                private val list: List<Fragment>) : FragmentStateAdapter(vpAdapter) {

    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        return list[position]
    }
}