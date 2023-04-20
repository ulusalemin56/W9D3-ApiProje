package com.example.w9d3_apiproje.data


import com.google.gson.annotations.SerializedName

data class MarsResponseItem(
    @SerializedName("id")
    val id: String,
    @SerializedName("img_src")
    val imgSrc: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("type")
    val type: String
)