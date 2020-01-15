package com.sundae.client;

import com.sundae.TestMethod;

import java.util.concurrent.TimeUnit;

/**
 * Main
 *
 * @author daijiyuan
 * @date 2020/1/14
 * @comment
 */
public class ClientMain {

    public static void main(String[] args) throws InterruptedException {
        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(3);
                TestMethod testMethod = ServiceProxyManager.proxy(TestMethod.class);
                testMethod.test1();
                TimeUnit.SECONDS.sleep(0);
                testMethod.test1("HHH");
                TimeUnit.SECONDS.sleep(0);
                System.out.println("testMethod.test2() ---> " + testMethod.test2());
                TimeUnit.SECONDS.sleep(0);
                System.out.println("testMethod.test3(String, int, boolean) ---> " + testMethod.test3("AAA", 23, false));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();

        new ClientBootStrap().bootstrap();


    }

}
