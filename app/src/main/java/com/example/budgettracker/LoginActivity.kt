package com.example.budgettracker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var signUpTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailEditText = findViewById(R.id.usernameInput)
        passwordEditText = findViewById(R.id.passwordInput)
        loginButton = findViewById(R.id.loginBtn)
        signUpTextView = findViewById(R.id.signUpText)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (validateLogin(email, password)) {
                // Save login status and proceed to the main app
                val sharedPref = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putBoolean("isLoggedIn", true)
                editor.apply()

                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                // Proceed to main activity
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish() // Close login screen
            } else {
                Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateLogin(email: String, password: String): Boolean {
        val sharedPref = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val storedEmail = sharedPref.getString("email", "")
        val storedPassword = sharedPref.getString("password", "")

        return email == storedEmail && password == storedPassword
    }

    fun goToSignUpPage(view: android.view.View) {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }
}
