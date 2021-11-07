package com.example.geotreasures.network

import com.example.geotreasures.BuildConfig
import com.example.geotreasures.data.CacheDetailsModel
import com.example.geotreasures.data.CacheSummaryModel
import com.example.geotreasures.utility.ConvertUtility
import com.google.android.libraries.maps.model.LatLng
import com.google.gson.JsonObject

object DataFetcher {
    // TODO
    // Store key in separate config file
    private const val consumerKey: String = BuildConfig.CONSUMER_KEY_OPENCACHING_PL

    suspend fun fetchNearlyCaches(location: LatLng): Set<CacheSummaryModel> {
        // TODO: change api request to
        //Search for caches within specified bounding box
        //:: services/caches/search/bbox method
        val searchMethod = "services/caches/search/nearest"
        val searchParams = JsonObject()
        searchParams.addProperty("center", "${location.latitude}|${location.longitude}")
        val retrievalMethod = "services/caches/geocaches"
        val retrievalParams = JsonObject()
        retrievalParams.addProperty("fields", "name|location|type")

        val response = APIAdapter.apiClient.getCaches(
            searchMethod,
            searchParams,
            retrievalMethod,
            retrievalParams,
            wrap = false,
            consumerKey
        )

        if (response.isSuccessful && response.body() != null) {
            val data: Map<String, JsonObject> = response.body() as Map<String, JsonObject>
            val cacheSet: MutableSet<CacheSummaryModel> = mutableSetOf()
            data.values.forEachIndexed { index, element ->
                cacheSet.add(
                    CacheSummaryModel(
                        code = data.keys.elementAt(index),
                        location = ConvertUtility.getLatLngFromString(element.get("location").toString()),
                        name = element.get("name").toString().replace("\"", "")
                    )
                )
            }
            return cacheSet
        }
        return setOf()
    }

    suspend fun fetchCacheDetails(code: String):CacheDetailsModel?{
        val response = APIAdapter.apiClient.getCacheDetails(
            code,
            "code|name|location|type|status|descriptions",
            consumerKey
        )

        if (response.isSuccessful && response.body() != null) {
            val responseJson = response.body()!!
            //val gson = GsonBuilder().setPrettyPrinting().create()
            //val model = gson.fromJson(response.body(), CacheDetailsModel::class.java)
            return CacheDetailsModel(
                code = responseJson.get("code").asString.toString(),
                name = responseJson.get("name").asString.toString(),
                location = responseJson.get("location").asString.toString(),
                description = responseJson.get("descriptions").asJsonObject.get("pl").asString.toString()
            )
        }
        return null
    }
}