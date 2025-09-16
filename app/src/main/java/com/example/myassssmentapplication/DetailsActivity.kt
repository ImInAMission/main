package com.example.myassssmentapplication

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val entity = intent.getSerializableExtra("entity") as? Entity
        val container: LinearLayout = findViewById(R.id.detailContainer)

        entity?.let { e ->
            e.properties.forEach { (key, value) ->
                val tv = TextView(this)
                tv.text = "$key: $value"
                tv.textSize = 18f
                container.addView(tv)
            }

            val tvDescription = TextView(this)
            tvDescription.text = "Description: ${e.description ?: "No Description"}"
            tvDescription.textSize = 16f
            container.addView(tvDescription)
        }
    }
}
