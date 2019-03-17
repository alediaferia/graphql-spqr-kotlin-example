package com.alediaferia.graphql.spqr.kotlin;

import io.leangen.graphql.annotations.GraphQLQuery;

public class JavaTestService {
    @GraphQLQuery
    public JavaTestClass findDummy(String name) {
        return null;
    }
}
