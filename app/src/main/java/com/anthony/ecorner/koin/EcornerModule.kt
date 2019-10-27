package com.anthony.ecorner.koin

import android.content.Context
import android.text.TextUtils
import android.util.Log
import androidx.core.net.toUri
import com.anthony.ecorner.BuildConfig
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit

val ecornerModule = module {
    single { createOkHttpClient() }
}

fun createOkHttpClient(openInterceptor: Boolean = true): OkHttpClient {
    val httpClient = OkHttpClient.Builder()
    httpClient.connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .followRedirects(false)
        .followSslRedirects(false)
        .addInterceptor(
            HttpLoggingInterceptor().setLevel(
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            )
        )

    if (openInterceptor) {
        httpClient.addInterceptor(RedirectInterceptor())
    }

    return httpClient.build()
}

object Properties {
    private var userName = ""

    fun clearProperties() {
        this.userName = ""
    }

    fun getUsername() = userName

    fun setToken(userName: String) {
        this.userName = userName
    }

}

inline fun <reified T> createService(okHttpClient: OkHttpClient): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.SERVER_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
    return retrofit.create(T::class.java)
}

private fun createResponse(chain: Interceptor.Chain, request: Request): Response {
    return chain.proceed(
        request.newBuilder().header("Accept", "application/json")
            .method(request.method, request.body).build()
    )
}


class RedirectInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        var response = createResponse(chain, request)

        when (response.code) {
        in 400..900 -> {
            throw IOException(response.toString())
        }
    }

    return response

    }
}

