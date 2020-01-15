package com.sundae.service;

import java.util.Arrays;

/**
 * ServiceResultBean
 *
 * @author daijiyuan
 * @date 2020/1/15
 * @comment
 */
public class ServiceResultBean {

    private String typeClassName;

    private byte[] resultBytes;

    public String getTypeClassName() {
        return typeClassName;
    }

    public void setTypeClassName(String typeClassName) {
        this.typeClassName = typeClassName;
    }

    public byte[] getResultBytes() {
        return resultBytes;
    }

    public void setResultBytes(byte[] resultBytes) {
        this.resultBytes = resultBytes;
    }

    @Override
    public String toString() {
        return "ServiceResultBean{" +
                "typeClassName='" + typeClassName + '\'' +
                ", resultBytes=" + Arrays.toString(resultBytes) +
                '}';
    }
}
