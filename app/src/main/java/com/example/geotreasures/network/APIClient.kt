package com.example.geotreasures.network

import com.example.geotreasures.data.CacheDetailsModel
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIClient {

    @GET("services/caches/shortcuts/search_and_retrieve")
    suspend fun getCaches(
        @Query("search_method") searchMethod: String,
        @Query("search_params") searchParams: JsonObject,
        @Query("retr_method") retrievalMethod: String,
        @Query("retr_params") retrievalParams: JsonObject,
        @Query("wrap") wrap: Boolean,
        @Query("consumer_key") consumerKey:String
    ): Response<Map<String, JsonObject>>

    @GET("services/caches/geocache")
    suspend fun getCacheDetails(
        @Query("cache_code") cacheCode: String,
        @Query("fields") fields:String,
        @Query("consumer_key") consumerKey:String
    ): Response<JsonObject>
}