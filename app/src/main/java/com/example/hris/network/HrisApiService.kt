package com.example.hris.network

import com.example.hris.model.AddTimeLogs
import com.example.hris.model.ProfileModel
import com.example.hris.model.TimeLogsModel
import com.example.hris.model.UpdateProfileModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

private const val BASE_URL =
    "http://my-tera.com:8080/MobileAppTraining/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

val builder = OkHttpClient.Builder()
    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .client(builder.build())
    .build()

interface HrisApiService {

    @FormUrlEncoded
    @POST("AppTrainingLogin.htm")
    suspend fun getProfile(
        @Field("userID") userID: String,
        @Field("password") password: String
    ): ProfileModel


    @FormUrlEncoded
    @POST("AppTrainingUpdateProfile.htm")
    suspend fun updateProfile(
        @Field("userID") userID: String,
        @Field("firstName") firstName: String,
        @Field("middleName") middleName: String?,
        @Field("lastName") lastName: String,
        @Field("emailAddress") emailAddress: String,
        @Field("mobileNumber") mobileNumber: String,
        @Field("landline") landline: String?
    ): UpdateProfileModel

    @FormUrlEncoded
    @POST("AppTrainingGetTimeLogs.htm")
    suspend fun getTimeLogs(
        @Field("userID") userID: String
    ): TimeLogsModel

    @FormUrlEncoded
    @POST("AppTrainingAddTimeLog.htm")
    suspend fun addTimeLogs(
        @Field("userID") userID: String,
        @Field("type") type: String
    ): AddTimeLogs
}

object HrisApi {
    val retrofitService: HrisApiService by lazy { retrofit.create(HrisApiService::class.java) }

}