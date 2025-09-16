package com.example.myassssmentapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.myassssmentapplication.api.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var tvResult: TextView
    private lateinit var spinnerLocation: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        tvResult = findViewById(R.id.tvResult)
        spinnerLocation = findViewById(R.id.spinnerLocation)

        setupLocationSpinner()

        btnLogin.setOnClickListener {
            val username = etUsername.text.toString().trim()
            var password = etPassword.text.toString().trim()
            val location = spinnerLocation.selectedItem.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                tvResult.text = "Please fill all fields"
                return@setOnClickListener
            }

            // The assignment requests studentID without leading 's' — strip it if user typed 's12345678'
            if (password.startsWith("s", ignoreCase = true)) {
                password = password.substring(1)
            }

            val request = LoginRequest(username, password)

            // Call the dynamic login endpoint
            RetrofitClient.instance.login(location, request).enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if (response.isSuccessful) {
                        val keypass = response.body()?.keypass
                        if (keypass != null) {
                            val intent = Intent(this@MainActivity, DashboardActivity::class.java)
                            intent.putExtra("keypass", keypass)
                            startActivity(intent)
                        } else {
                            tvResult.text = "Login failed: no keypass returned"
                        }
                    } else {
                        tvResult.text = "Login failed: invalid credentials (HTTP ${response.code()})"
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    tvResult.text = "Network error: ${t.message}"
                }
            })
        }
    }

    private fun setupLocationSpinner() {
        // Provide three class locations: 'footscray', 'sydney', 'br'
        val locations = listOf("footscray", "sydney", "br")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, locations)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerLocation.adapter = adapter
    }
}
