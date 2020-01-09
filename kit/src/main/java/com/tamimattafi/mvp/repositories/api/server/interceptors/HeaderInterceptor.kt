package com.tamimattafi.mvp.repositories.api.server.interceptors

import okhttp3.Interceptor
import okhttp3.Response

abstract class HeaderInterceptor(private val onRequestValue: () -> String) : Interceptor {

    abstract var header: String
    open var prefix: String = ""
    open var separator: String = ""
    open var defaultCondition: Boolean = true
    open fun getCondition(): Boolean = defaultCondition

    override fun intercept(chain: Interceptor.Chain): Response {
            val initialRequest = chain.request()
            return if (getCondition()) {
                val modifiedRequest = initialRequest.newBuilder()
                    .addHeader(
                        header,
                        StringBuilder().append(prefix).append(separator).append(onRequestValue.invoke()).toString()
                    )
                    .build()

                chain.proceed(modifiedRequest)
            } else chain.proceed(initialRequest)

    }


    companion object  {

        fun newInstance(header: String, onRequestValue: () -> String): HeaderInterceptor
            = object : HeaderInterceptor(onRequestValue) {
                override var header: String = header
            }

        fun newInstance(header: String, prefix: String, onRequestValue: () -> String): HeaderInterceptor
                = newInstance(header, onRequestValue).apply { this.prefix = prefix }

        fun newInstance(header: String, prefix: String, separator: String, onRequestValue: () -> String): HeaderInterceptor
                = newInstance(header, prefix, onRequestValue).apply { this.separator = separator }

        fun newInstance(header: String, prefix: String, separator: String, defaultCondition: Boolean, onRequestValue: () -> String): HeaderInterceptor
                = newInstance(header, prefix, separator, onRequestValue).apply { this.defaultCondition = defaultCondition }

        fun newInstance(header: String, onRequestValue: () -> String, prefix: String = "", separator: String = "", defaultCondition: Boolean = true): HeaderInterceptor
                = newInstance(header, prefix, separator, defaultCondition, onRequestValue).apply { this.defaultCondition = defaultCondition }

    }



}