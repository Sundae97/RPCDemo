package com.sundae;

/**
 * TestMethodImpl
 *
 * @author daijiyuan
 * @date 2020/1/14
 * @comment
 */
public class TestMethodImpl implements TestMethod {
    @Override
    public void test1() {
        System.out.println("hello --> test1");
    }

    @Override
    public void test1(String i) {
        System.out.println("hello --> test1 : " + i);
    }

    @Override
    public String test2() {
        return "hello ---> test2";
    }
}
