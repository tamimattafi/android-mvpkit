package com.tamimattafi.mvp.model.database

import androidx.sqlite.db.SimpleSQLiteQuery
import com.tamimattafi.mvp.model.database.QueryBuilder.Expressions.ALL
import com.tamimattafi.mvp.model.database.QueryBuilder.Expressions.AND
import com.tamimattafi.mvp.model.database.QueryBuilder.Expressions.DELETE
import com.tamimattafi.mvp.model.database.QueryBuilder.Expressions.FROM
import com.tamimattafi.mvp.model.database.QueryBuilder.Expressions.INNER_JOIN
import com.tamimattafi.mvp.model.database.QueryBuilder.Expressions.IS_NOT_NULL
import com.tamimattafi.mvp.model.database.QueryBuilder.Expressions.IS_NULL
import com.tamimattafi.mvp.model.database.QueryBuilder.Expressions.LIKE
import com.tamimattafi.mvp.model.database.QueryBuilder.Expressions.LIMIT
import com.tamimattafi.mvp.model.database.QueryBuilder.Expressions.OFFSET
import com.tamimattafi.mvp.model.database.QueryBuilder.Expressions.ON
import com.tamimattafi.mvp.model.database.QueryBuilder.Expressions.OR
import com.tamimattafi.mvp.model.database.QueryBuilder.Expressions.ORDER_BY
import com.tamimattafi.mvp.model.database.QueryBuilder.Expressions.SELECT
import com.tamimattafi.mvp.model.database.QueryBuilder.Expressions.WHERE
import com.tamimattafi.mvp.model.database.QueryBuilder.Operators.IN

open class QueryBuilder(var rawQuery: String = "") {

    fun selectFrom(table: String): QueryBuilder = addToRawQuery { "$SELECT $ALL $FROM $table" }

    fun deleteFrom(table: String): QueryBuilder = addToRawQuery { "$DELETE $FROM $table" }

    fun innerJoinOn(table: String, field: String, operator: String, joinTable: String, joinField: String): QueryBuilder = addToRawQuery { "$INNER_JOIN $joinTable $ON $table.$field $operator $joinTable.$joinField" }

    fun joinWhere(joinTable: String, joinField: String, operator: String, value: Any): QueryBuilder = whereClause(WHERE, "$joinTable.$joinField", operator, value)

    fun where(field: String, operator: String, value: Any): QueryBuilder = whereClause(WHERE, field, operator, value)

    fun and(field: String, operator: String, value: Any): QueryBuilder
            = whereClause(AND, field, operator, value)

    fun or(field: String, operator: String, value: Any): QueryBuilder
            = whereClause(OR, field, operator, value)

    fun orderBy(field: String, direction: String = Direction.ASCENDING): QueryBuilder
            = addToRawQuery { "$ORDER_BY $field $direction" }

    fun limit(limit: Int, offset: Int = 0): QueryBuilder
            = addToRawQuery { "$LIMIT $limit $OFFSET $offset" }

    fun whereIsNull(field: String): QueryBuilder = nullClause(WHERE, field)

    fun whereIsNotNull(field: String): QueryBuilder = notNullClause(WHERE, field)

    fun andIsNull(field: String): QueryBuilder = nullClause(AND, field)

    fun andIsNotNull(field: String): QueryBuilder = notNullClause(AND, field)

    fun orIsNull(field: String): QueryBuilder = nullClause(OR, field)

    fun orIsNotNull(field: String): QueryBuilder = notNullClause(OR, field)

    fun whereIn(field: String, values: List<Any>): QueryBuilder = whereClause(WHERE, field, IN, values.joinToString(prefix = "(", separator = ",", postfix = ")"))

    fun search(field: String, searchQuery: String): QueryBuilder
            = addToRawQuery { "$WHERE $field $LIKE '%$searchQuery%'" }

    fun whereClause(expression: String, field: String, operator: String, value: Any): QueryBuilder
            = addToRawQuery { "$expression $field $operator '$value'" }

    fun nullClause(expression: String, field: String): QueryBuilder
            = addToRawQuery { "$expression $field $IS_NULL" }

    fun notNullClause(expression: String, field: String): QueryBuilder
            = addToRawQuery { "$expression $field $IS_NOT_NULL" }

    fun custom(rawQuery: String): QueryBuilder
            = addToRawQuery { rawQuery }

    protected fun addToRawQuery(query: () -> String): QueryBuilder
            = this.also { this.rawQuery = StringBuilder().append(this.rawQuery).append(query.invoke()).append(" ").toString() }

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

    object Expressions {
        const val WHERE = "WHERE"
        const val AND = "AND"
        const val OR = "OR"
        const val SELECT = "SELECT"
        const val DELETE = "DELETE"
        const val ALL = "*"
        const val FROM = "FROM"
        const val LIMIT = "LIMIT"
        const val OFFSET = "OFFSET"
        const val LIKE = "LIKE"
        const val ORDER_BY = "ORDER BY"
        const val INNER_JOIN = "INNER JOIN"
        const val ON = "ON"
        const val IS_NULL = "IS NULL"
        const val IS_NOT_NULL = "IS NOT NULL"
    }

}
