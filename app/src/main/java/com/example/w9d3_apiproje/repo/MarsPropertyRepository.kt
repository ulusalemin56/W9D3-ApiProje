package com.example.w9d3_apiproje.repo

import android.content.Context
import androidx.room.Room
import com.example.w9d3_apiproje.api.MarsApiService
import com.example.w9d3_apiproje.data.MarsResponseItem
import com.example.w9d3_apiproje.db.MarsPropertyDao
import com.example.w9d3_apiproje.db.MarsPropertyDataBase
import com.example.w9d3_apiproje.util.NetworkUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class MarsPropertyRepository(private val context: Context) {
    private val marsApiService = MarsApiService.create()

    private var marsPropertyDao: MarsPropertyDao

    init {
        val marsPropertyDatabase = Room.databaseBuilder(
            context.applicationContext,
            MarsPropertyDataBase::class.java,
            "name_property_database"
        ).build()

        marsPropertyDao = marsPropertyDatabase.marsPropertyDao()
    }

    fun fetchProperties(): Flow<List<MarsResponseItem>> {
        return flow {
            if (NetworkUtil.isInternetAvailable(context)) {
                emit(fetchFromService())
            } else {
                emit(fetchFromDatabase())
            }
        }.flowOn(Dispatchers.IO)
    }

    private suspend fun fetchFromService(): List<MarsResponseItem> {

        val response = marsApiService.getProperties()

        insertProperties(response)

        return response
    }

    private suspend fun fetchFromDatabase(): List<MarsResponseItem> {

        return marsPropertyDao.getAllProperties()

    }

    private suspend fun insertProperties(properties: List<MarsResponseItem>) {

        try {
            marsPropertyDao.insertProperties(properties)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


}