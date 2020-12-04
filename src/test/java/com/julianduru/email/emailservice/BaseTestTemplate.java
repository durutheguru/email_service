package com.julianduru.email.emailservice;


/**
 * created by julian
 */
public class BaseTestTemplate<T> implements TestTemplate<T> {


    public void test() throws Throwable {
        TestSetup<T> setupResult = setup();

        perform(setupResult);
        verify(setupResult);
        teardown(setupResult);
    }


}
