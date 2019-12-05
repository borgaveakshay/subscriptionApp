package com.example.mp.retrofit

import com.subscribe.snpa.SNPA.models.*
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface NetworkApi {


    @POST("/add/customer")
    fun registerCustomer(@Body customerDTO: CustomerDTO): Observable<ResponseDTO<CustomerDTO>>

    @POST("/add/vendor")
    fun registerVendor(@Body vendorDTO: VendorDTO): Observable<ResponseDTO<VendorDTO>>

    @POST("/auth/vendor")
    fun doVendorLogin(@Body authDTO: AuthDTO): Observable<ResponseDTO<VendorDTO>>

    @POST("/auth/customer")
    fun doCustomerLogin(@Body authDTO: AuthDTO): Observable<ResponseDTO<CustomerDTO>>

    @GET("/area/list")
    fun locateArea(): Observable<ResponseListDTO<AreaDTO>>

    @GET("/newspaper/list")
    fun getNewsPapers(): Observable<ResponseListDTO<NewsPaperDTO>>

    @GET("/newspapers")
    fun subscribeNewsPaper(@Body subscribeDTO: SubscribeDTO): Observable<NewsPaper>

    companion object {
        fun getService(): NetworkApi {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://d2787831.ngrok.io")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(NetworkApi::class.java)
        }
    }
}