package com.rodion2236.loftmoney.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rodion2236.loftmoney.remote.LoftmoneyItem
import com.rodion2236.loftmoney.R
import com.rodion2236.loftmoney.databinding.ItemLoftmoneyBinding
import java.util.ArrayList


class LoftRVAdapter: RecyclerView.Adapter<LoftRVAdapter.LoftHolder>() {

    val loftList = ArrayList<LoftmoneyItem>()

    class LoftHolder(item: View): RecyclerView.ViewHolder(item) {

        val binding = ItemLoftmoneyBinding.bind(item)
        fun bind(loftmoneyItem: LoftmoneyItem) = with(binding){

            loftmoneyTitleView.text = loftmoneyItem.title
            loftmoneyValueView.text = loftmoneyItem.cost.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoftHolder {

        val view = LayoutInflater.from(parent.context).
        inflate(R.layout.item_loftmoney, parent, false)
        return LoftHolder(view)
    }

    override fun onBindViewHolder(holder: LoftHolder, position: Int) {
        holder.bind(loftList[position])
    }

    override fun getItemCount(): Int {
        return loftList.size
    }

    fun addLoftItem(loftmoneyItem: List<LoftmoneyItem>) {
        loftList.clear()
        loftList.addAll(loftmoneyItem)
        notifyDataSetChanged()
    }
}