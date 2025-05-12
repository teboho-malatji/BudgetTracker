package com.example.budgettracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CategoryAdapter(private val categoryList: List<Category>) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryTitle: TextView = itemView.findViewById(R.id.categoryName)
        val transactionLayout: LinearLayout = itemView.findViewById(R.id.transactionList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category_expandable, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categoryList[position]

        // Set title
        holder.categoryTitle.text = category.name

        // Handle expand/collapse
        holder.transactionLayout.visibility = if (category.isExpanded) View.VISIBLE else View.GONE

        // Populate transaction list dynamically
        holder.transactionLayout.removeAllViews()
        for (transaction in category.transactions) {
            val transactionTextView = TextView(holder.itemView.context).apply {
                text = "${transaction.label}: $%.2f".format(transaction.amount)
                setPadding(16, 4, 16, 4)
            }
            holder.transactionLayout.addView(transactionTextView)
        }

        // Toggle on click
        holder.categoryTitle.setOnClickListener {
            category.isExpanded = !category.isExpanded
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int = categoryList.size
}
