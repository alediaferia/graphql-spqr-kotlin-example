package com.alediaferia.graphql.spqr.kotlin

import graphql.schema.GraphQLArgument
import graphql.schema.GraphQLFieldDefinition
import graphql.schema.GraphQLNonNull
import io.leangen.graphql.generator.BuildContext
import io.leangen.graphql.generator.OperationMapper
import io.leangen.graphql.generator.mapping.SchemaTransformer
import io.leangen.graphql.metadata.Operation
import io.leangen.graphql.metadata.OperationArgument
import java.lang.reflect.Method
import java.lang.reflect.Parameter
import kotlin.reflect.full.instanceParameter
import kotlin.reflect.jvm.jvmErasure
import kotlin.reflect.jvm.kotlinFunction

class KotlinTypesSchemaTransformer : SchemaTransformer {
    override fun transformField(
        field: GraphQLFieldDefinition,
        operation: Operation,
        operationMapper: OperationMapper,
        buildContext: BuildContext
    ): GraphQLFieldDefinition {
        val kotlinFunction = (operation.typedElement.element as? Method)?.kotlinFunction
        if (kotlinFunction?.returnType?.isMarkedNullable == false)
            return field.transform { it.type(GraphQLNonNull(field.type)) }

        return super.transformField(field, operation, operationMapper, buildContext)
    }

    override fun transformArgument(
        argument: GraphQLArgument,
        operationArgument: OperationArgument,
        operationMapper: OperationMapper,
        buildContext: BuildContext
    ): GraphQLArgument {
        val kotlinFunction = ((operationArgument.typedElement.element as? Parameter)?.declaringExecutable as? Method)?.kotlinFunction
        if (kotlinFunction?.instanceParameter?.type?.jvmErasure?.java?.isKotlinClass() == true)
            for (arg in kotlinFunction.parameters)
                if (arg.name == operationArgument.name) {
                    if (!arg.type.isMarkedNullable)
                        return argument.transform { builder -> builder.type(GraphQLNonNull.nonNull(argument)) }
                    break
                }
        return super.transformArgument(argument, operationArgument, operationMapper, buildContext)
    }
}
