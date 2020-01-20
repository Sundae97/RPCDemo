package com.sundae.service;

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

    public ServiceMethodProvider(Class interfaceClz, Object implObject) {
        this.interfaceClz = interfaceClz;
        this.implObject = implObject;
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
}
