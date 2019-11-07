package com.tamimattafi.mvp.api.repository

open class QueryBuilder(private val raw: HashMap<String, String> = HashMap()) {

    fun orderBy(field: String): QueryBuilder
        = this.also { raw[ORDER_BY] = field }

    fun limit(limit: Long): QueryBuilder
        = this.also { raw[LIMIT] = limit.toString() }

    fun offset(offset: Long): QueryBuilder
        = this.also { raw[OFFSET] = offset.toString() }

    fun custom(parameter: String, value: Any)
        = this.also { raw[parameter] = value.toString() }

    fun build(): Map<String, String> = raw

    fun getRaw(): HashMap<String, String> = raw

    companion object Parameters {
        const val ORDER_BY = "orderBy"
        const val LIMIT = "limit"
        const val OFFSET = "offset"
    }

}