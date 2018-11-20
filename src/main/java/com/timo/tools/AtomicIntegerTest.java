package com.timo.tools;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Abraham Qin
 * @since 2018/11/19
 */
public class AtomicIntegerTest {
    public static final AtomicInteger atomicInteger = new AtomicInteger(0);
    public static void main(String[] args) {
        for ( int i=0;i<3;i++) {
            int andIncrement = atomicInteger.getAndIncrement();
            System.out.println(andIncrement);
        }
    }
}
