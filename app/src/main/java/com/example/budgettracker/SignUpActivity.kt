package com.example.budgettracker

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.view.View


class SignUpActivity : AppCompatActivity() {

    private lateinit var signupEmailEditText: EditText
    private lateinit var signupPasswordEditText: EditText
    private lateinit var signupButton: Button
    private lateinit var logInTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        signupEmailEditText = findViewById(R.id.signUpUsernameInput)
        signupPasswordEditText = findViewById(R.id.signUpPasswordInput)
        signupButton = findViewById(R.id.signUpBtn)
        logInTextView = findViewById(R.id.loginText)

        signupButton.setOnClickListener {
            val email = signupEmailEditText.text.toString()
            val password = signupPasswordEditText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                // Save user credentials in SharedPreferences
                val sharedPref = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("email", email)
                editor.putString("password", password)
                editor.apply()

                Toast.makeText(this, "Sign Up Successful", Toast.LENGTH_SHORT).show()

                // After successful sign-up, go back to login screen
                finish()
            } else {
                Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show()
            }
        }

    }
    fun goToLoginPage(view: View) {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

}
