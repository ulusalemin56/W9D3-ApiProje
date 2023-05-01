package com.example.w9d3_apiproje.repo

import android.content.Context
import androidx.room.Room
import com.example.w9d3_apiproje.data.MarsResponseItem
import com.example.w9d3_apiproje.db.MarsPropertyDao
import com.example.w9d3_apiproje.db.MarsPropertyDataBase
import java.lang.Exception
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

object MarsPropertyRepostory {

    private lateinit var marsPropertyDao: MarsPropertyDao
    private val executor: ExecutorService = Executors.newSingleThreadExecutor()

    fun initializeDataBase(contex: Context) {

        val marsPropertyDatabase = Room.databaseBuilder(
            contex.applicationContext,
            MarsPropertyDataBase::class.java,
            "name_property_database"
        ).build()

        marsPropertyDao = marsPropertyDatabase.marsPropertyDao()
    }

    fun insertProperties(properties: List<MarsResponseItem>, callback: (success: Boolean) -> Unit) {

        executor.execute {
            try {
                marsPropertyDao.insertProperties(properties)
                callback(true)
            } catch (e: Exception) {
                e.printStackTrace()
                callback(false)
            }
        }

    }

    fun getAllProperties(callback: (properties: List<MarsResponseItem>) -> Unit) {
        executor.execute {
            val marsProperties = marsPropertyDao.getAllProperties()
            callback(marsProperties)
        }

    }

}