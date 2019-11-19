package com.anthony.ecorner.koin

import com.anthony.ecorner.BuildConfig
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.CookieManager
import java.net.CookiePolicy
import java.util.concurrent.TimeUnit

val ecornerModule = module {
    single { createOkHttpClient() }
}

fun createOkHttpClient(openInterceptor: Boolean = true): OkHttpClient {
    val httpClient = OkHttpClient.Builder()
    val cookieManager = CookieManager()
    cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL)
    val cookieJar = JavaNetCookieJar(cookieManager)
    httpClient.connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .cookieJar(cookieJar)
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
    private var id = -1
    private var name = ""
    private var username =""

    fun clearProperties() {
        this.id = -1
        this.name =""
        this.username =""
    }

    fun getId() = id

    fun getName()= name

    fun getUsername()=username

    fun setUserInfo(id: Int,name:String,username:String) {
        this.id = id
        this.name =name
        this.username = username
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
        request.newBuilder()
            .header("Content-Type", "multipart/form-data")
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

