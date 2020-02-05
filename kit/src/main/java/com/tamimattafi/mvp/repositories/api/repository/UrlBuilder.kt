package com.tamimattafi.mvp.repositories.api.repository

open class UrlBuilder(baseUrl: String = "") {

    private var url: String = baseUrl

    fun fromUrl(baseUrl: String): UrlBuilder
        = this.also { it.url = baseUrl }

    fun path(path: String): UrlBuilder
        = this.also {
            url = url.rebuild().apply {
                val newPath = path.removeSuffix("/")
                if (endsWith("/")) {
                    if (newPath.startsWith("/")) append(newPath.removePrefix("/"))
                    else append(newPath)
                } else {
                    if (newPath.startsWith("/")) append(newPath)
                    else append("/$newPath")
                }
            }.toString()
        }

    fun query(query: Map<String, String>): UrlBuilder
        = this.also {
            if (query.isNotEmpty()) {
                url = url.rebuild().apply {
                    if (!url.endsWith("?")) append("?")
                    query.keys.forEachIndexed { index, key ->
                        append("$key=${query[key]}")
                        append(if (index == query.size -1) "" else "&")
                    }
                }.toString()
            }
        }

    private fun String.rebuild(): StringBuilder
        = StringBuilder().append(this)

    fun build(): String = url

    fun rebuild(): UrlBuilder = UrlBuilder(url)

}