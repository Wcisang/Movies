package com.example.android.movies.network

import okhttp3.Interceptor
import okhttp3.Response

class MovieAuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url = request.url()
            .newBuilder()
            .addQueryParameter("api_key", "")
            .build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}