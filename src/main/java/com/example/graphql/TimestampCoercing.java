package com.example.graphql;

import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;

/**
 * Created by xiongyizhou on 2019/8/9 10:23
 * E-mail: xiongyizhou@powerpms.com
 *
 * @author xiongyizhou
 */
public class TimestampCoercing<I,O> implements Coercing {
    @Override
    public O serialize(Object dataFetcherResult) throws CoercingSerializeException {
        return null;
    }

    @Override
    public Object parseValue(Object input) throws CoercingParseValueException {

        return null;
    }

    @Override
    public Object parseLiteral(Object input) throws CoercingParseLiteralException {
        return null;
    }
}
