package com.bitresolution.xtest.examples;

import com.bitresolution.xtest.Node;
import com.bitresolution.xtest.PromiseException;
import com.bitresolution.xtest.Promises;
import com.bitresolution.xtest.transform.ThrowTransform;

import java.io.IOException;
import java.util.concurrent.Callable;

import static com.bitresolution.xtest.PromisesBuilder.brokenPromise;
import static com.bitresolution.xtest.PromisesBuilder.promise;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

@Node
public class TestNodeExceptionHandlingMethodExample {

    @Node
    public Promises classic() {
        try {
            ioExceptionThrowingMethod();
            return Promises.EMPTY_PROMISE;
        }
        catch (IOException e) {
            return promise().broken().expected(IOException.class);
        }
    }

    @Node
    @PromiseException(IOException.class)
    public Promises runtimeGraphTransformation() throws IOException {
        ioExceptionThrowingMethod();
        return brokenPromise().expected(IOException.class);
    }

    @Node
    @ThrowTransform(
            targetClass = TestNodeExceptionHandlingMethodExample.class,
            targetMethod = "ioExceptionThrowingMethod",
            type = IOException.class)
    public Promises compileTimeGraphTransformation() throws IOException {
        //given
        Object state = new Object();
        //when
        ioExceptionThrowingMethod();
        //then
        return promise().that(state, is(state));
    }

    @Node
    public Promises delayedExecution() {
        return promise().when(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                ioExceptionThrowingMethod();
                return null;
            }
        }).exception(instanceOf(IOException.class));
    }

    private void ioExceptionThrowingMethod() throws IOException {
        throw new IOException("Mock exception");
    }
}
