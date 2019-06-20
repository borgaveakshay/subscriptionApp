package com.example.mp.retrofit

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface NetworkApi {

    @POST("/authCustomer")
    fun doLogin(@Query("email") email: String): Observable<User>

    @GET("/areas")
    fun locateArea(): Observable<Areas>

    @GET("/newspapers")
    fun subscribeNewsPaper(@Query("areaId") areaId: Int,@Query("areaName") areaName : String) : Observable<NewsPaper>

    companion object {
        fun getService(): NetworkApi {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://d1da70ee.ngrok.io")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(NetworkApi::class.java)
        }
    }
}