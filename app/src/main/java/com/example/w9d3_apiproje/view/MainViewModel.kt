package com.example.w9d3_apiproje.view


import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.example.w9d3_apiproje.api.MarsApiService
import com.example.w9d3_apiproje.data.MarsResponseItem
import com.example.w9d3_apiproje.db.MarsPropertyDao
import com.example.w9d3_apiproje.db.MarsPropertyDataBase
import com.example.w9d3_apiproje.repo.MarsPropertyRepostory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val marsApiService = MarsApiService.create()

    private val _properties = MutableLiveData<List<MarsResponseItem>>()
    val properties: LiveData<List<MarsResponseItem>> = _properties


    fun getFilterServiceCall(filter: String) {

        val marsPropertiesWithFilterCall = marsApiService.getPropertiesWithFilter(filter)

        marsPropertiesWithFilterCall.enqueue(object : Callback<List<MarsResponseItem>> {
            override fun onResponse(
                call: Call<List<MarsResponseItem>>,
                response: Response<List<MarsResponseItem>>
            ) {

                if (response.isSuccessful) {
                    _properties.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<MarsResponseItem>>, t: Throwable) {

            }

        })
    }

    fun marsServiceCall() {
        marsApiService.getProperties().enqueue(object : Callback<List<MarsResponseItem>> {

            override fun onResponse(
                call: Call<List<MarsResponseItem>>,
                response: Response<List<MarsResponseItem>>
            ) {

                if (response.isSuccessful) {
                    _properties.value = response.body()

                    response.body()?.let {
                        MarsPropertyRepostory.insertProperties(it) {success ->

                            if (success) {
                                Log.e("Database İşlemi", "Kayıt Başarılı")
                            } else {
                                Log.e("Database İşlemi", "Hata")
                            }
                        }

                    }

                }

            }

            override fun onFailure(call: Call<List<MarsResponseItem>>, t: Throwable) {

            }

        })
    }

    fun getFilterPrice(price: Int) {
        marsApiService.getProperties().enqueue(object : Callback<List<MarsResponseItem>> {

            override fun onResponse(
                call: Call<List<MarsResponseItem>>,
                response: Response<List<MarsResponseItem>>
            ) {

                if (response.isSuccessful) {

                    val marsResponse = mutableListOf<MarsResponseItem>()

                    response.body()?.forEach {
                        if (it.price <= price)
                            marsResponse.add(it)
                    }

                    _properties.value = marsResponse

                } else {

                }

            }

            override fun onFailure(call: Call<List<MarsResponseItem>>, t: Throwable) {

            }

        })
    }
}