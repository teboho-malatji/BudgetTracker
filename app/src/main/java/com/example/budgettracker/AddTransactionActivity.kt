package com.example.budgettracker

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.budgettracker.databinding.ActivityAddTransactionBinding
import android.text.Editable
import android.text.TextWatcher

class AddTransactionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTransactionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Clear error messages when user types
        binding.dateInput.addTextChangedListener(SimpleTextWatcher {
            if (it.isNotEmpty()) binding.dateLayout.error = null
        })
        binding.labelInput.addTextChangedListener(SimpleTextWatcher {
            if (it.isNotEmpty()) binding.labelLayout.error = null
        })
        binding.amountInput.addTextChangedListener(SimpleTextWatcher {
            if (it.isNotEmpty()) binding.amountLayout.error = null
        })
        binding.descriptionInput.addTextChangedListener(SimpleTextWatcher {
            if (it.isNotEmpty()) binding.descriptionLayout.error = null
        })

        // Add transaction button logic
        binding.addTransactionBtn.setOnClickListener {
            val date = binding.dateInput.text.toString()
            val label = binding.labelInput.text.toString()
            val amount = binding.amountInput.text.toString().toDoubleOrNull()
            val description = binding.descriptionInput.text.toString()
            val category = binding.categoryDropdown.text.toString()

            var hasError = false

            if (date.isEmpty()) {
                binding.dateLayout.error = "Please enter a valid date"
                hasError = true
            }

            if (label.isEmpty()) {
                binding.labelLayout.error = "Please enter a valid label"
                hasError = true
            }

            if (amount == null) {
                binding.amountLayout.error = "Please enter a valid amount"
                hasError = true
            }

            if (description.isEmpty()) {
                binding.descriptionLayout.error = "Please enter a valid description"
                hasError = true
            }

            if (category.isEmpty()) {
                binding.categoryLayout.error = "Please choose a valid category"
                hasError = true
            }

            if (!hasError) {
                // Save transaction to database (to be implemented)
            }
        }

        // Close button
        binding.closeBtn.setOnClickListener {
            finish()
        }
    }

    // Helper class for simplified text watcher
    private fun SimpleTextWatcher(afterTextChanged: (String) -> Unit): TextWatcher {
        return object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                afterTextChanged.invoke(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }
    }
}
