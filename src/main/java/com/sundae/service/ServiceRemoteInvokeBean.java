package com.sundae.service;

import java.util.Arrays;

/**
 * ServiceRemoteInvokeBean
 *
 * @author daijiyuan
 * @date 2020/1/15
 * @comment
 */
public class ServiceRemoteInvokeBean {

    private String methodDescription;
    private Object[] args;

    public ServiceRemoteInvokeBean() {
    }

    public ServiceRemoteInvokeBean(String methodDescription, Object[] args) {
        this.methodDescription = methodDescription;
        this.args = args;
    }

    public String getMethodDescription() {
        return methodDescription;
    }

    public void setMethodDescription(String methodDescription) {
        this.methodDescription = methodDescription;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    @Override
    public String toString() {
        return "ServiceRemoteInvokeBean{" +
                "methodDescription='" + methodDescription + '\'' +
                ", args=" + Arrays.toString(args) +
                '}';
    }
}
