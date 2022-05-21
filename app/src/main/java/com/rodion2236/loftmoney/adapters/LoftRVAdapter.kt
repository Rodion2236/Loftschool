package com.rodion2236.loftmoney.adapters

import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.rodion2236.loftmoney.main.models.LoftmoneyItem
import com.rodion2236.loftmoney.R
import com.rodion2236.loftmoney.databinding.ItemLoftmoneyBinding
import com.rodion2236.loftmoney.main.fragment_budget.LoftMoneyItemClickAdapter
import java.util.ArrayList

class LoftRVAdapter(private val colorId: Int): RecyclerView.Adapter<LoftRVAdapter.LoftHolder>() {

    private val loftList: MutableList<LoftmoneyItem> = ArrayList()
    val loftmoneyItemList: List<LoftmoneyItem>
        get() = loftList
    private var loftmoneyCellClickAdapter: LoftMoneyItemClickAdapter? = null

    class LoftHolder(itemView: View, colorId: Int,
                     loftMoneyItemClickAdapter: LoftMoneyItemClickAdapter?
    ): RecyclerView.ViewHolder(itemView) {

        val binding = ItemLoftmoneyBinding.bind(itemView)
        private val loftMoneyItemClickAdapter: LoftMoneyItemClickAdapter?

        fun bind(loftmoneyItem: LoftmoneyItem) {
            binding.loftmoneyTitleView.text = loftmoneyItem.title
            binding.loftmoneyValueView.text = loftmoneyItem.cost.toString() + "₽"
            itemView.setBackgroundColor(ContextCompat.getColor(itemView.context,
            if (loftmoneyItem.isSelected) R.color.primary_color_selected_items
            else android.R.color.white)
            )
            itemView.setOnClickListener { loftMoneyItemClickAdapter?.CellClick(loftmoneyItem) }
            itemView.setOnLongClickListener { loftMoneyItemClickAdapter?.LongCellClick(loftmoneyItem)
            true
            }
        }

        init {
            binding.loftmoneyValueView.setTextColor(ContextCompat
                .getColor(binding.loftmoneyValueView.context, colorId))
            this.loftMoneyItemClickAdapter = loftMoneyItemClickAdapter
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoftHolder {
        val view = View.inflate(parent.context, R.layout.item_loftmoney, null)
        return LoftHolder(view, colorId, loftmoneyCellClickAdapter)
    }

    override fun onBindViewHolder(holder: LoftHolder, position: Int) {
        holder.bind(loftList[position])
    }

    override fun getItemCount(): Int {
        return loftList.size
    }

    fun updateItem(loftmoneyItem: LoftmoneyItem) {
        val itemPosition = loftList.indexOf(loftmoneyItem)
        loftList[itemPosition] = loftmoneyItem
        notifyItemChanged(itemPosition)
    }

    fun addLoftItem(loftmoneyItem: List<LoftmoneyItem>?) {
        loftList.clear()
        loftList.addAll(loftmoneyItem!!)
        notifyDataSetChanged()
    }

    fun setMoneyCellAdapterClick(loftmoneyCellClickAdapter: LoftMoneyItemClickAdapter?) {
        this.loftmoneyCellClickAdapter = loftmoneyCellClickAdapter
    }
}