package com.example.graphql;

import graphql.language.StringValue;
import graphql.schema.*;

import java.util.Date;
import java.util.regex.Pattern;

/**
 * Created by xiongyizhou on 2019/8/7 16:38
 * E-mail: xiongyizhou@powerpms.com
 *
 * @author xiongyizhou
 */
public class TimestampScalar {


    public static final GraphQLScalarType Timestamp = new GraphQLScalarType("Timestamp", "A custom scalar that handles Timestamp", new Coercing() {
        @Override
        public Object serialize(Object dataFetcherResult) {
            return serializeTimestamp(dataFetcherResult);
        }

        @Override
        public Object parseValue(Object input) {
            return parseTimestampFromVariable(input);
        }

        @Override
        public Object parseLiteral(Object input) {
            return parseTimestampFromAstLiteral(input);
        }
    });


    private static boolean looksLikeAnEmailAddress(String possibleEmailValue) {
        // ps.  I am not trying to replicate RFC-3696 clearly
        return Pattern.matches("[A-Za-z0-9]@[.*]", possibleEmailValue);
    }

    private static Object serializeTimestamp(Object dataFetcherResult) {
        return new Date(Long.getLong(String.valueOf(dataFetcherResult)));
    }

    private static Object parseTimestampFromVariable(Object input) {
        if (input instanceof String) {
            String possibleEmailValue = input.toString();
            if (looksLikeAnEmailAddress(possibleEmailValue)) {
                return possibleEmailValue;
            }
        }
        throw new CoercingParseValueException("Unable to parse variable value " + input + " as an email address");
    }

    private static Object parseTimestampFromAstLiteral(Object input) {
        if (input instanceof StringValue) {
            return ((StringValue) input).getValue();
        }
        throw new CoercingParseLiteralException(
                "Value is not Timestamp : '" + input + "'"
        );
    }
}
