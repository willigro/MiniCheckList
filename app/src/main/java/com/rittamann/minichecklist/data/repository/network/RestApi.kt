package com.rittamann.minichecklist.data.repository.network

import android.content.Context
import com.rittamann.minichecklist.BuildConfig
import retrofit2.Call
import retrofit2.http.Headers
import com.rittamann.minichecklist.data.base.NoteBody
import com.rittamann.minichecklist.data.base.NoteResponse
import com.rittamann.minichecklist.utils.Constants
import com.rittamann.minichecklist.utils.SharedPreferencesUtil
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

const val CONTENT_TYPE_JSON = "Content-Type:application/json"

interface RestApi {
    @Headers(CONTENT_TYPE_JSON)
    @POST("note")
    fun registerNote(@Body noteBody: NoteBody): Call<Void>

    @Headers(CONTENT_TYPE_JSON)
    @PUT("note/{id}")
    fun updateNote(@Path("id") id: Long, @Body noteBody: NoteBody): Call<Void>

    @Headers(CONTENT_TYPE_JSON)
    @GET("note")
    fun getAllNotes(): Call<List<NoteResponse>>

    companion object {
        operator fun invoke(
            context: Context
        ): RestApi {
            // TODO MOCK
            var host = SharedPreferencesUtil.getHost(context)
            if (host.isEmpty()) {
                host = BuildConfig.BASE_API_URL
                SharedPreferencesUtil.setHost(context, host)
            }

            return Retrofit.Builder()
                .baseUrl(host)
                .client(ClientApi().create(context)) // TODO: ROLLBACK!!!!!!
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RestApi::class.java)
        }
    }
}