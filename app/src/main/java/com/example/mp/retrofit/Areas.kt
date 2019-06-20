package com.example.mp.retrofit

import com.google.gson.annotations.SerializedName

data class Areas(@SerializedName("statusCode") val statusCode : Int,
                 @SerializedName("statusMessage") val statusMessage : String,
                 @SerializedName("result") val result : List<AreasResult>)

data class AreasResult (
    @SerializedName("areaId") val areaId : Int,
    @SerializedName("areaName") val areaName : String
)