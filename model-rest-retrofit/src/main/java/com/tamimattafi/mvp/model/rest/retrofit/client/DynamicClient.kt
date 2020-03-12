package com.tamimattafi.mvp.model.rest.retrofit.client

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface DynamicClient {

    @GET
    fun get(@Url url: String): Call<ResponseBody>

    @PUT
    fun put(@Url url: String, @Body any: Any): Call<Void>

    @PUT
    fun put(@Url url: String): Call<Void>

    @PUT
    fun putAndListen(@Url url: String, @Body any: Any): Call<ResponseBody>

    @PUT
    fun putAndListen(@Url url: String): Call<ResponseBody>

    @POST
    fun post(@Url url: String, @Body any: Any): Call<Void>

    @POST
    fun post(@Url url: String): Call<Void>

    @POST
    fun postAndListen(@Url url: String, @Body any: Any): Call<ResponseBody>

    @POST
    fun postAndListen(@Url url: String): Call<ResponseBody>

    @DELETE
    fun delete(@Url url: String): Call<Void>

    @HTTP(method = "DELETE", hasBody = true)
    fun delete(@Url url: String, @Body any: Any): Call<Void>

    @DELETE
    fun deleteAndListen(@Url url: String): Call<ResponseBody>

    @HTTP(method = "DELETE", hasBody = true)
    fun deleteAndListen(@Url url: String, @Body any: Any): Call<ResponseBody>


}