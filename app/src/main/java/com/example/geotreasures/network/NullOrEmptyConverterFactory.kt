package com.example.geotreasures.network


import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type


class NullOrEmptyConverterFactory : Converter.Factory() {

    fun converterFactory() = this

    override fun responseBodyConverter(type: Type?,
                                       annotations: Array<Annotation>,
                                       retrofit: Retrofit): Converter<ResponseBody, Any>? {
        return Converter { responseBody ->
            if (responseBody.contentLength() == 0L) {
                null
            } else {
                type?.let {
                    retrofit.nextResponseBodyConverter<Any>(this, it, annotations).convert(responseBody)
                }
            }
        }
    }
}