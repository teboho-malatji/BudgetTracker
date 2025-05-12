package com.example.budgettracker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.budgettracker.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var transactions: ArrayList<Transaction>
    private lateinit var transactionAdapter: TransactionAdapter

    private lateinit var categoryList: ArrayList<Category>
    private lateinit var categoryAdapter: ExpandableCategoryAdapter

    companion object {
        const val ADD_CATEGORY_REQUEST_CODE = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize transaction list
        transactions = arrayListOf(
            Transaction("Weekend Budget", 400.00),
            Transaction("Bananas", -4.00),
            Transaction("Petrol", -40.90),
            Transaction("Breakfast", -9.99),
            Transaction("Water Bottles", -4.00),
            Transaction("Sunscreen", -8.00),
            Transaction("Car Park", -15.00)
        )

        // Setup RecyclerView for transactions
        transactionAdapter = TransactionAdapter(transactions)
        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        binding.recyclerview.adapter = transactionAdapter

        // Setup RecyclerView for categories
        categoryList = arrayListOf()
        categoryAdapter = ExpandableCategoryAdapter(categoryList)
        binding.categoryRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.categoryRecyclerView.adapter = categoryAdapter

        // Update dashboard values
        updateDashboard()

        // Add transaction button
        binding.addBtn.setOnClickListener {
            val intent = Intent(this, AddTransactionActivity::class.java)
            startActivity(intent)
        }

        // Add category button
        binding.categoryBtn.setOnClickListener {
            val intent = Intent(this, AddCategoryActivity::class.java)
            startActivityForResult(intent, ADD_CATEGORY_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_CATEGORY_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val name = data.getStringExtra("category_name") ?: return
            val amount = data.getDoubleExtra("category_amount", 0.0)
            val category = Category(name, amount, transactions = emptyList())
            categoryList.add(category)
            categoryAdapter.notifyItemInserted(categoryList.size - 1)
        }
    }

    private fun updateDashboard() {
        val totalAmount = transactions.sumOf { it.amount }
        val budgetAmount = transactions.filter { it.amount > 0 }.sumOf { it.amount }
        val expenseAmount = budgetAmount - totalAmount

        binding.balance.text = "$ %.2f".format(totalAmount)
        binding.budget.text = "$ %.2f".format(budgetAmount)
        binding.expense.text = "$ %.2f".format(expenseAmount)
    }
}
