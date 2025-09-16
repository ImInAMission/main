package com.example.myassssmentapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myassssmentapplication.api.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EntityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = EntityAdapter(emptyList()) { entityMap ->
            val entity = Entity(properties = entityMap, description = entityMap["description"]?.toString())
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("entity", entity)
            startActivity(intent)
        }

        recyclerView.adapter = adapter

        val keypass = intent.getStringExtra("keypass")
        if (keypass.isNullOrEmpty()) {
            Toast.makeText(this, "No keypass provided", Toast.LENGTH_LONG).show()
            return
        }

        loadDashboard(keypass)
    }

    private fun loadDashboard(keypass: String) {
        RetrofitClient.instance.getDashboard(keypass)
            .enqueue(object : Callback<DashboardResponse> {
                override fun onResponse(
                    call: Call<DashboardResponse>,
                    response: Response<DashboardResponse>
                ) {
                    if (response.isSuccessful) {
                        val entities = response.body()?.entities ?: emptyList()
                        adapter.updateData(entities)
                    } else {
                        Toast.makeText(
                            this@DashboardActivity,
                            "Failed to load: HTTP ${response.code()}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onFailure(call: Call<DashboardResponse>, t: Throwable) {
                    Toast.makeText(
                        this@DashboardActivity,
                        "Network error: ${t.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }
}
