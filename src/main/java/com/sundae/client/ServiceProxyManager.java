package com.sundae.client;

import com.sundae.service.ServiceInvokeHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * ServiceProxyManager
 *
 * @author daijiyuan
 * @date 2020/1/15
 * @comment
 */
public class ServiceProxyManager {

    public static <T> T proxy(Class clz){
        Object interfaceProxy = (T) Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class<?>[]{clz},
                new ServiceInvokeHandler()
        );
        return (T) interfaceProxy;
    }

}
