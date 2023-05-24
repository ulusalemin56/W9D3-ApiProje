package com.example.w9d3_apiproje.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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


        viewModel.properties.observe(this) { marsResponse ->

            setUpRecyclerView(marsResponse)

        }

    }


    private fun setUpRecyclerView(marsProperties: List<MarsResponseItem>) {

        val adapter = MarsPropertyAdapter(marsProperties)
        recyclerView.adapter = adapter


    }
}