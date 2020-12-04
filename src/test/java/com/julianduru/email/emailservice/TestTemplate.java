package com.julianduru.email.emailservice;


/**
 * created by julian
 */
public interface TestTemplate<T> {


    default TestSetup<T> setup() throws Throwable {
        return new TestSetup<>(null);
    }


    default void perform(TestSetup<T> setup) throws Throwable {}


    default void verify(TestSetup<T> setup) throws Throwable {}


    default void teardown(TestSetup<T> setup) throws Throwable {}


}
