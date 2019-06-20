package com.example.mp.retrofit

import com.google.gson.annotations.SerializedName

data class NewsPaper (
    @SerializedName("statusCode") val statusCode : Int,
    @SerializedName("statusMessage") val statusMessage : String,
    @SerializedName("result") val result : List<NewsPaperResult>
)

data class NewsPaperResult (

    @SerializedName("newsPaperId") val newsPaperId : Int,
    @SerializedName("newsPaperName") val newsPaperName : String,
    @SerializedName("newsPaperPrice") val newsPaperPrice : Double
)