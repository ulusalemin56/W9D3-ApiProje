package com.example.w9d3_apiproje.view


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.w9d3_apiproje.data.MarsResponseItem
import com.example.w9d3_apiproje.repo.MarsPropertyRepository


class MainViewModel(private val marsPropertyRepository: MarsPropertyRepository) : ViewModel() {

    val properties: LiveData<List<MarsResponseItem>> = marsPropertyRepository.fetchProperties().asLiveData()

}