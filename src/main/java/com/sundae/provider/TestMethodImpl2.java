package com.sundae.provider;

import java.util.concurrent.TimeUnit;

/**
 * TestMethodImpl
 *
 * @author daijiyuan
 * @date 2020/1/14
 * @comment
 */
public class TestMethodImpl2 implements TestMethod2 {
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
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello ---> test2";
    }

    @Override
    public TestBean test3(String a, int b, boolean c) {
        TestBean testBean = new TestBean();
        testBean.setId(b);
        testBean.setName(a);
        testBean.setAdmin(c);
        System.out.println(testBean.toString());
        return testBean;
    }
}
