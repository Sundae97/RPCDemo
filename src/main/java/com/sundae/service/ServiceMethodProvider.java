package com.sundae.service;

import com.sundae.server.ProviderHostInfo;

/**
 * ServiceProvider
 *
 * @author daijiyuan
 * @date 2020/1/15
 * @comment
 */
public class ServiceMethodProvider {

    private Class interfaceClz;
    private Object implObject;
    private int weight;

    public ServiceMethodProvider(Class interfaceClz, Object implObject) {
        this(interfaceClz, implObject, 1);
    }

    public ServiceMethodProvider(Class interfaceClz, Object implObject, int weight) {
        this.interfaceClz = interfaceClz;
        this.implObject = implObject;
        this.weight = weight;
    }

    public Class getInterfaceClz() {
        return interfaceClz;
    }

    public void setInterfaceClz(Class interfaceClz) {
        this.interfaceClz = interfaceClz;
    }

    public Object getImplObject() {
        return implObject;
    }

    public void setImplObject(Object implObject) {
        this.implObject = implObject;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "ServiceMethodProvider{" +
                "interfaceClz=" + interfaceClz +
                ", implObject=" + implObject +
                ", weight=" + weight +
                '}';
    }
}
