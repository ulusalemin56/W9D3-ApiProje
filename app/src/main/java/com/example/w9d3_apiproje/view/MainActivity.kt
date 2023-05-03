package com.example.w9d3_apiproje.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.w9d3_apiproje.view.adapters.MarsPropertyAdapter
import com.example.w9d3_apiproje.R
import com.example.w9d3_apiproje.data.MarsResponseItem
import com.example.w9d3_apiproje.repo.MarsPropertyRepository
import com.example.w9d3_apiproje.view.factory.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val marsPropertyRepository = MarsPropertyRepository(this)
        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(marsPropertyRepository)
        )[MainViewModel::class.java]

        recyclerView = findViewById(R.id.recycler_view)

       /* val buttonBuy = findViewById<Button>(R.id.buttonBuy)
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
        }*/


        viewModel.properties.observe(this) { marsResponse ->

            setUpRecyclerView(marsResponse)

        }

    }


    private fun setUpRecyclerView(marsProperties: List<MarsResponseItem>) {

        val adapter = MarsPropertyAdapter(marsProperties)
        recyclerView.adapter = adapter


    }
}