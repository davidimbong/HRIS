package com.example.hris.network

import com.example.hris.model.LoginModel
import com.example.hris.model.LoginRequestBody
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.POST

//var okHttpClient: OkHttpClient = UnsafeOkHttpClient().getUnsafeOkHttpClient()

private const val BASE_URL =
    "http://my-tera.com:8080/MobileAppTraining/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
//    .client(okHttpClient)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface HrisApiService {
    @POST("AppTrainingLogin.htm")
    suspend fun getLogin(@Body requestBody: LoginRequestBody): LoginModel

//    @POST("AppTrainingLogin.htm")
//    suspend fun getLogin(@Field("User ID") userID: String, @Field("Password") password: String): LoginModel

//    @POST("AppTrainingLogin.htm/userID=david&password=Tera1234")
//    suspend fun getLogin(): LoginModel
}

object HrisApi {
    val retrofitService: HrisApiService by lazy { retrofit.create(HrisApiService::class.java) }

}