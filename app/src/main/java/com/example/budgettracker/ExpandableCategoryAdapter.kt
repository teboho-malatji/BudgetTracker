package com.example.budgettracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExpandableCategoryAdapter(private val categoryList: List<Category>) :
    RecyclerView.Adapter<ExpandableCategoryAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryName: TextView = itemView.findViewById(R.id.categoryName)
        val transactionList: LinearLayout = itemView.findViewById(R.id.transactionList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category_expandable, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categoryList[position]
        holder.categoryName.text = category.name


        // Show/hide transactions
        holder.transactionList.removeAllViews()
        if (category.isExpanded) {
            holder.transactionList.visibility = View.VISIBLE
            for (transaction in category.transactions) {
                val transactionView = TextView(holder.itemView.context)
                transactionView.text = "• ${transaction.label}: $%.2f".format(transaction.amount)
                transactionView.setPadding(32, 4, 0, 4)
                holder.transactionList.addView(transactionView)
            }
        } else {
            holder.transactionList.visibility = View.GONE
        }

        // Toggle expand/collapse
        holder.itemView.setOnClickListener {
            category.isExpanded = !category.isExpanded
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int = categoryList.size
}
