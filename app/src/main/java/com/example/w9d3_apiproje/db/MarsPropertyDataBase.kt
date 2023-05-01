package com.example.w9d3_apiproje.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.w9d3_apiproje.data.MarsResponseItem

@Database(entities = [MarsResponseItem::class], version = 1)
abstract class MarsPropertyDataBase : RoomDatabase(){
    abstract fun marsPropertyDao() : MarsPropertyDao
}