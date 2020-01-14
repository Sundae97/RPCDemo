package com.sundae;

/**
 * ProtocolData
 *
 * @author daijiyuan
 * @date 2020/1/14
 * @comment
 */
public class ProtocolData {

    private String version;

    private byte[] bodyData;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public byte[] getBodyData() {
        return bodyData;
    }

    public void setBodyData(byte[] bodyData) {
        this.bodyData = bodyData;
    }
}
