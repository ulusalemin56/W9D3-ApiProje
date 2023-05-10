package com.example.w9d3_apiproje.view


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.w9d3_apiproje.data.MarsResponseItem
import com.example.w9d3_apiproje.repo.MarsPropertyRepository
import kotlinx.coroutines.launch

class MainViewModel(private val marsPropertyRepository: MarsPropertyRepository) : ViewModel() {


    private val _properties = MutableLiveData<List<MarsResponseItem>>()
    val properties: LiveData<List<MarsResponseItem>> = _properties


    init {
        fetchProperties()
    }

    private fun fetchProperties() {
        viewModelScope.launch {
            val responseItem = marsPropertyRepository.fetchProperties()
            _properties.value = responseItem
        }

    }

}