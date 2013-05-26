package com.bitresolution.xtest.core;

import com.bitresolution.commons.XIterables;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Arrays.copyOfRange;

public class FormattedMessageException extends Exception {

    public FormattedMessageException(String message) {
        super(message);
    }

    public FormattedMessageException(String message, Object... parameters) {
        super(MessageFormatter.format(message, parameters), MessageFormatter.extractCause(message, parameters));
    }

    protected final static class MessageFormatter {

        private static final Joiner JOINER = Joiner.on("");
        private static final Splitter SPLITTER = Splitter.on("{}");

        public static String format(String message, Object[] parameters) {
            int variableCount = countVariables(message);
            if(variableCount == 0) {
                return message;
            }
            Iterable<String> strings = SPLITTER.split(message);
            List<Object> variables = asList(copyOfRange(parameters, 0, variableCount));
            return JOINER.join(XIterables.interleave(strings, variables));
        }

        public static Throwable extractCause(String message, Object[] parameters) {
//            if(parameters.length > 0 && countVariables(message) > 0) {
            if(parameters.length > 0) {
                try {
                    return (Throwable) parameters[parameters.length - 1];
                }
                catch (ClassCastException e) {
                    return null;
                }
            }
            return null;
        }

        private static int countVariables(String message) {
            return StringUtils.countMatches(message, "{}");
        }
    }
}
