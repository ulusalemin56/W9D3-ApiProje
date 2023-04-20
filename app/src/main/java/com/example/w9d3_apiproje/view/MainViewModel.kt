package com.example.w9d3_apiproje.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.w9d3_apiproje.api.MarsApiService
import com.example.w9d3_apiproje.data.MarsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val marsApiService = MarsApiService.create()

    private val _properties = MutableLiveData<MarsResponse>()
    val properties: LiveData<MarsResponse> = _properties

    fun getFilterServiceCall(filter: String) {

        val marsPropertiesWithFilterCall = marsApiService.getPropertiesWithFilter(filter)

        marsPropertiesWithFilterCall.enqueue(object : Callback<MarsResponse> {
            override fun onResponse(call: Call<MarsResponse>, response: Response<MarsResponse>) {

                if (response.isSuccessful) {
                    _properties.value = response.body()
                }
            }

            override fun onFailure(call: Call<MarsResponse>, t: Throwable) {

            }

        })
    }

    fun marsServiceCall() {
        marsApiService.getProperties().enqueue(object : Callback<MarsResponse> {

            override fun onResponse(call: Call<MarsResponse>, response: Response<MarsResponse>) {

                if (response.isSuccessful) {
                    _properties.value = response.body()
                } else {

                }

            }

            override fun onFailure(call: Call<MarsResponse>, t: Throwable) {

            }

        })
    }

    fun getFilterPrice(price: Int) {
        marsApiService.getProperties().enqueue(object : Callback<MarsResponse> {

            override fun onResponse(call: Call<MarsResponse>, response: Response<MarsResponse>) {

                if (response.isSuccessful) {

                    val marsResponse = MarsResponse()

                    response.body()?.forEach {
                        if (it.price <= price)
                            marsResponse.add(it)
                    }

                    _properties.value = marsResponse

                } else {

                }

            }

            override fun onFailure(call: Call<MarsResponse>, t: Throwable) {

            }

        })
    }
}