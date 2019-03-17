package com.alediaferia.graphql.spqr.kotlin

import io.leangen.graphql.annotations.GraphQLArgument
import io.leangen.graphql.annotations.GraphQLMutation
import io.leangen.graphql.annotations.GraphQLQuery


class DummyService {
    @GraphQLQuery
    fun findDummy(@GraphQLArgument(name = "name") name: String?): DummyType? = null

    @GraphQLMutation
    fun createDummy(@GraphQLArgument(name = "name") name: String): DummyType =
        DummyType(name)
}