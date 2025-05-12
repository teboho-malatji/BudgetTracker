package com.example.budgettracker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import android.widget.Button
import android.widget.ImageButton

class AddCategoryActivity : AppCompatActivity() {

    private lateinit var categoryInput: TextInputEditText
    private lateinit var moneyInput: TextInputEditText
    private lateinit var categoryLayout: TextInputLayout
    private lateinit var moneyLayout: TextInputLayout
    private lateinit var addCategoryBtn: Button
    private lateinit var backBtn: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_category)

        // Initialize views
        categoryInput = findViewById(R.id.categoryInput)
        moneyInput = findViewById(R.id.moneyInput)
        categoryLayout = findViewById(R.id.categoryLayout)
        moneyLayout = findViewById(R.id.moneyLayout)
        addCategoryBtn = findViewById(R.id.addCategoryBtn)
        backBtn = findViewById(R.id.backBtn)

        // Input field validations
        categoryInput.setOnFocusChangeListener { _, _ ->
            if (categoryInput.text?.isNotEmpty() == true) {
                categoryLayout.error = null
            }
        }

        moneyInput.setOnFocusChangeListener { _, _ ->
            if (moneyInput.text?.isNotEmpty() == true) {
                moneyLayout.error = null
            }
        }

        // Add category button
        addCategoryBtn.setOnClickListener {
            val category = categoryInput.text.toString().trim()
            val moneyText = moneyInput.text.toString().trim()
            val money = moneyText.toDoubleOrNull()

            var hasError = false

            if (category.isEmpty()) {
                categoryLayout.error = "Category is required"
                hasError = true
            } else {
                categoryLayout.error = null
            }

            if (money == null) {
                moneyLayout.error = "Please enter a valid amount"
                hasError = true
            } else {
                moneyLayout.error = null
            }

            if (!hasError) {
                val resultIntent = Intent()
                resultIntent.putExtra("category_name", category)
                resultIntent.putExtra("category_amount", money)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }

        // Close button
        backBtn.setOnClickListener {
            finish()
        }
    }
}
