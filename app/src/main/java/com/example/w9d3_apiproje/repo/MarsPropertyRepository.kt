package com.example.w9d3_apiproje.repo

import android.content.Context
import androidx.room.Room
import com.example.w9d3_apiproje.api.MarsApiService
import com.example.w9d3_apiproje.data.MarsResponseItem
import com.example.w9d3_apiproje.db.MarsPropertyDao
import com.example.w9d3_apiproje.db.MarsPropertyDataBase
import com.example.w9d3_apiproje.util.NetworkUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MarsPropertyRepository(private val context: Context) {
    private val marsApiService = MarsApiService.create()

    private lateinit var marsPropertyDao: MarsPropertyDao
    private val executor: ExecutorService = Executors.newSingleThreadExecutor()


    init {
        val marsPropertyDatabase = Room.databaseBuilder(
            context.applicationContext,
            MarsPropertyDataBase::class.java,
            "name_property_database"
        ).build()

        marsPropertyDao = marsPropertyDatabase.marsPropertyDao()
    }

    fun fetchProperties(callback: (properties: List<MarsResponseItem>) -> Unit) {

        if (NetworkUtil.isInternetAvailable(context)) {

            fetchFromService(callback)

        } else {

            fetchFromDatabase(callback)

        }

    }

    private fun fetchFromService(callback: (properties: List<MarsResponseItem>) -> Unit) {
        marsApiService.getProperties().enqueue(object : Callback<List<MarsResponseItem>> {

            override fun onResponse(
                call: Call<List<MarsResponseItem>>,
                response: Response<List<MarsResponseItem>>
            ) {

                if (response.isSuccessful) {

                    val responseList = response.body()
                    if (responseList != null) {
                        callback(responseList)
                        insertProperties(responseList)
                    }

                } else {
                    fetchFromDatabase(callback)
                }
            }

            override fun onFailure(call: Call<List<MarsResponseItem>>, t: Throwable) {
                fetchFromDatabase(callback)
            }

        })
    }

    private fun fetchFromDatabase(callback: (properties: List<MarsResponseItem>) -> Unit) {
        executor.execute {
            val marsProperties = marsPropertyDao.getAllProperties()
            callback(marsProperties)
        }
    }

    private fun insertProperties(properties: List<MarsResponseItem>) {

        executor.execute {
            try {
                marsPropertyDao.insertProperties(properties)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }


}