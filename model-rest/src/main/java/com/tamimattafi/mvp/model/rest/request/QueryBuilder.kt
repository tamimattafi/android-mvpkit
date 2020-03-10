package com.tamimattafi.mvp.model.rest.request

open class QueryBuilder(val raw: HashMap<String, String> = HashMap()) {

    open fun orderBy(field: String): QueryBuilder
        = this.also { raw[ORDER_BY] = field }

    open fun limit(limit: Long): QueryBuilder
        = this.also { raw[LIMIT] = limit.toString() }

    open fun offset(offset: Long): QueryBuilder
        = this.also { raw[OFFSET] = offset.toString() }

    open fun custom(parameter: String, value: Any)
        = this.also { raw[parameter] = value.toString() }

    open fun build(): Map<String, String> = raw

    companion object Parameters {
        const val ORDER_BY = "orderBy"
        const val LIMIT = "limit"
        const val OFFSET = "offset"
    }

}