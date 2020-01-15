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
