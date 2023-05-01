package com.example.w9d3_apiproje.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.w9d3_apiproje.data.MarsResponseItem

@Dao
interface MarsPropertyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProperties(properties : List<MarsResponseItem>)

    @Query("SELECT * FROM mars_property")
    fun getAllProperties() : List<MarsResponseItem>
}