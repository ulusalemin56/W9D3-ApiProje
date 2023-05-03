package com.example.w9d3_apiproje.view.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.w9d3_apiproje.repo.MarsPropertyRepository
import com.example.w9d3_apiproje.view.MainViewModel

class MainViewModelFactory(
    private val marsPropertyRepository: MarsPropertyRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(marsPropertyRepository) as T
        }
        throw IllegalArgumentException("Unknown View model class")
    }

}