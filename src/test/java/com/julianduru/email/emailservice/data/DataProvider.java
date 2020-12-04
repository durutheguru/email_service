package com.julianduru.email.emailservice.data;


import java.util.ArrayList;
import java.util.List;

/**
 * created by julian
 */
public interface DataProvider<T> {



    T provide();


    T provide(T sample);


    default List<T> provide(int count) {
        List<T> list = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            list.add(provide());
        }

        return list;
    }


    default List<T> provide(T sample, int count) {
        List<T> list = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            list.add(provide(sample));
        }

        return list;
    }



}
