package com.tamimattafi.mvp.model.rest.request

open class UrlBuilder(var raw: String = "") {


    fun baseUrl(raw: String): UrlBuilder
        = this.also { it.raw = raw }

    fun path(path: String): UrlBuilder
        = this.also {
            raw = raw.rebuild().apply {
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
                raw = raw.rebuild().apply {
                    if (!raw.endsWith("?")) append("?")
                    query.keys.forEachIndexed { index, key ->
                        append("$key=${query[key]}")
                        append(if (index == query.size -1) "" else "&")
                    }
                }.toString()
            }
        }

    private fun String.rebuild(): StringBuilder
        = StringBuilder().append(this)

    fun build(): String = raw

    fun rebuild(): UrlBuilder = UrlBuilder(raw)

}