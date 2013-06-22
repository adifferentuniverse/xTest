package com.bitresolution.xtest.commons.junit;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.springframework.context.ApplicationContext;

public class SpringInjectionRule implements TestRule {

    private final ApplicationContext context;
    private final Object target;

    public SpringInjectionRule(Object target) throws RuntimeException {
        this.target = target;
        this.context = SpringContextBuilder.forTestClass(target.getClass());
    }

    public Statement apply(final Statement statement, Description description) {
        return new WiredStatement(statement, context, target);
    }

    private static class WiredStatement extends Statement {
        private final Statement statement;
        private ApplicationContext context;
        private final Object target;

        public WiredStatement(Statement statement, ApplicationContext context, Object target) {
            this.statement = statement;
            this.context = context;
            this.target = target;
        }

        @Override
        public void evaluate() throws Throwable {
            context.getAutowireCapableBeanFactory().autowireBean(target);
            statement.evaluate();
        }
    }
}

