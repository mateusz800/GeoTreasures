package com.example.geotreasures.map

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.geotreasures.data.CacheDetailsModel
import com.example.geotreasures.data.CacheSummaryModel

object MapInteractionDataStore{
    var previousSelectedCache:MutableLiveData<CacheSummaryModel?> = MutableLiveData(null)
        private set
    var selectedCache: MutableLiveData<CacheSummaryModel?> = MutableLiveData(null)
        private set
    var activeCache:MutableLiveData<CacheDetailsModel?> = MutableLiveData(null)
        private set
    var activeCacheBackup:CacheDetailsModel? = null


    fun setSelectedCache(model: CacheSummaryModel?){
        previousSelectedCache.postValue(selectedCache.value)
        selectedCache.postValue(model)
    }

    fun setActiveCache(model:CacheDetailsModel?){
        if(model != null){
            activeCacheBackup = model
        }
        activeCache.postValue(model)
    }


}