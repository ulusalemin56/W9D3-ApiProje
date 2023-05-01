package com.example.w9d3_apiproje.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.w9d3_apiproje.view.adapters.MarsPropertyAdapter
import com.example.w9d3_apiproje.R
import com.example.w9d3_apiproje.data.MarsResponseItem
import com.example.w9d3_apiproje.repo.MarsPropertyRepostory

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MarsPropertyRepostory.initializeDataBase(this)

        viewModel.marsServiceCall()

        recyclerView = findViewById(R.id.recycler_view)
        val buttonBuy = findViewById<Button>(R.id.buttonBuy)
        val buttonRent = findViewById<Button>(R.id.buttonRent)
        val buttonPrice = findViewById<Button>(R.id.buttonPrice)
        val editText = findViewById<EditText>(R.id.editTextPrice)

        buttonBuy.setOnClickListener {
            viewModel.getFilterServiceCall("buy")
        }

        buttonRent.setOnClickListener {
            viewModel.getFilterServiceCall("rent")
        }

        buttonPrice.setOnClickListener {
            val price = editText.text.toString()
            if (price.isNotEmpty())
                viewModel.getFilterPrice(price.toInt())
            else
                Toast.makeText(this, "Lütfen Rakam Belirtiniz", Toast.LENGTH_SHORT).show()
        }


        viewModel.properties.observe(this) { marsResponse ->

            setUpRecyclerView(marsResponse)


        }

    }


    private fun setUpRecyclerView(marsProperties: List<MarsResponseItem>) {

        val adapter = MarsPropertyAdapter(marsProperties)
        recyclerView.adapter = adapter


    }
}