package com.tamimattafi.mvp.repositories.database

import androidx.sqlite.db.SimpleSQLiteQuery

class QueryBuilder(var rawQuery: String = "") {

    fun selectFrom(table: String): QueryBuilder
            = addToRawQuery { "SELECT * FROM $table" }

    fun deleteFrom(table: String): QueryBuilder
            = addToRawQuery { "DELETE FROM $table" }

    fun innerJoinOn(table: String, field: String, operator: String, joinTable: String, joinField: String): QueryBuilder
            = addToRawQuery { "INNER JOIN $joinTable ON $table.$field $operator $joinTable.$joinField" }

    fun joinWhere(joinTable: String, joinField: String, operator: String, value: Any): QueryBuilder
            = whereClause("WHERE", "$joinTable.$joinField", operator, value)

    fun where(field: String, operator: String, value: Any): QueryBuilder
            = whereClause("WHERE", field, operator, value)

    fun whereIn(field: String, values: List<Any>): QueryBuilder
            = whereClause("WHERE", field, Operators.IN, values.joinToString(prefix = "(", separator = ",", postfix = ")"))

    fun and(field: String, operator: String, value: Any): QueryBuilder
            = whereClause("AND", field, operator, value)

    fun or(field: String, operator: String, value: Any): QueryBuilder
            = whereClause("OR", field, operator, value)

    fun orderBy(field: String, direction: String = Direction.ASCENDING): QueryBuilder
            = addToRawQuery { "ORDER BY $field $direction" }

    fun limit(limit: Int, offset: Int = 0): QueryBuilder
            = addToRawQuery { "LIMIT $limit OFFSET $offset" }

    fun search(field: String, searchQuery: String): QueryBuilder
            = addToRawQuery { "WHERE $field LIKE '%$searchQuery%'" }

    private fun whereClause(operation: String, field: String, operator: String, value: Any): QueryBuilder
            = addToRawQuery { "$operation $field $operator '$value'" }

    private fun addToRawQuery(query: () -> String): QueryBuilder
            = this.also { it.rawQuery += "${query.invoke()} " }

    fun build(): SimpleSQLiteQuery
        = SimpleSQLiteQuery(rawQuery)

    object Direction {
        const val ASCENDING = "ASC"
        const val DESCENDING = "DESC"
    }

    object Operators {
        const val EQUAL_TO = "="
        const val IN = "IN"
        const val NOT_EQUAL_TO = "!="
        const val BIGGER_THAN = ">"
        const val LESS_THAN = "<"
        const val BIGGER_THAN_OR_EQUAL_TO = ">="
        const val LESS_THAN_OR_EQUAL_TO = "<="
    }

}
