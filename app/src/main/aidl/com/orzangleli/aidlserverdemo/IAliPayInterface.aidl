// IAliPayInterface.aidl
package com.orzangleli.aidlserverdemo;
import com.orzangleli.aidlserverdemo.OrderVo;
// Declare any non-default types here with import statements

interface IAliPayInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    boolean pay(in OrderVo orderVo);
}
