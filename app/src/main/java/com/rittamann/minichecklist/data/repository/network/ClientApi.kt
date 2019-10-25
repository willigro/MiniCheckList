package com.rittamann.minichecklist.data.repository.network

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.security.cert.CertificateException
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class ClientApi {
    private val CACHE_SIZE: Long = 5 * 1024 * 1024// 5MB

    // Create a trust manager that does not validate certificate chains
    // Install the all-trusting trust manager
    // Create an ssl socket factory with our all-trusting manager
    fun create(context: Context): OkHttpClient {
        val myCache = okhttp3.Cache(context.cacheDir, CACHE_SIZE)

        try {
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                @Throws(CertificateException::class)
                override fun checkClientTrusted(
                    chain: Array<java.security.cert.X509Certificate>,
                    authType: String
                ) {
                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(
                    chain: Array<java.security.cert.X509Certificate>,
                    authType: String
                ) {
                }

                override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                    return arrayOf()
                }
            })
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, java.security.SecureRandom())
            val sslSocketFactory = sslContext.socketFactory

            val builder = OkHttpClient.Builder()
            builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            builder.hostnameVerifier { hostname, session -> true }

            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            val requestTimeout = 60 * 1000L

            return builder
                .cache(myCache)
                .readTimeout(requestTimeout, TimeUnit.MINUTES)
                .writeTimeout(requestTimeout, TimeUnit.MINUTES)
                .connectTimeout(requestTimeout, TimeUnit.MINUTES)
                .addInterceptor(logging)
                .build()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}