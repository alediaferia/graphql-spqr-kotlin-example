package com.alediaferia.graphql.spqr.kotlin

import graphql.schema.GraphQLNonNull
import io.leangen.graphql.GraphQLSchemaGenerator
import org.junit.jupiter.api.Test

class KotlinTypesSchemaTransformerTest {

    @Test
    fun `non nullable kotlin types are mapped to non nullable GraphQL types`() {
        val schema = GraphQLSchemaGenerator()
            .withOperationsFromSingleton(DummyService())
            .withSchemaTransformers(KotlinTypesSchemaTransformer())
            .generate()

        val returnType = schema.queryType
            .getFieldDefinition("findDummy")
            .type

        assert(returnType !is GraphQLNonNull)

        val mutationReturnType = schema.mutationType
            .getFieldDefinition("createDummy")
            .type

        assert(mutationReturnType is GraphQLNonNull)

        val mutationArgumentType = schema.mutationType
            .getFieldDefinition("createDummy")
            .arguments[0].type

        assert(mutationArgumentType is GraphQLNonNull)

        val queryArgumentType = schema.queryType
            .getFieldDefinition("findDummy")
            .arguments[0].type

        assert(queryArgumentType !is GraphQLNonNull)
    }

    @Test
    fun `non-kotlin types are ignored`() {
        val schema = GraphQLSchemaGenerator()
            .withOperationsFromSingleton(JavaTestService())
            .withSchemaTransformers(KotlinTypesSchemaTransformer())
            .generate()

        val queryArgumentType = schema.queryType
            .getFieldDefinition("findDummy")
            .arguments[0].type

        assert(queryArgumentType !is GraphQLNonNull)
    }
}

