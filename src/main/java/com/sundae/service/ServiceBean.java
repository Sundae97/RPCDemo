package com.sundae.service;

import java.lang.reflect.Method;

/**
 * ServiceBean
 *
 * @author daijiyuan
 * @date 2020/1/15
 * @comment
 */
public class ServiceBean {

    private Object instance;
    private Method method;
    private String methodDescription;

    public ServiceBean() {
    }

    public ServiceBean(Object instance, Method method, String methodDescription) {
        this.instance = instance;
        this.method = method;
        this.methodDescription = methodDescription;
    }

    public Object getInstance() {
        return instance;
    }

    public void setInstance(Object instance) {
        this.instance = instance;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public String getMethodDescription() {
        return methodDescription;
    }

    public void setMethodDescription(String methodDescription) {
        this.methodDescription = methodDescription;
    }

    @Override
    public String toString() {
        return "ServiceBean{" +
                "instance=" + instance +
                ", method=" + method +
                ", methodDescription='" + methodDescription + '\'' +
                '}';
    }
}
