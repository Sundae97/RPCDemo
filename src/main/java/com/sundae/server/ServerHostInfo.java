package com.sundae.server;

/**
 * HostInfo
 *
 * @author daijiyuan
 * @date 2020/1/21
 * @comment
 */
public class ServerHostInfo {

    private String ip;
    private int port;
    //TODO more info


    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "HostInfo{" +
                "ip='" + ip + '\'' +
                ", port=" + port +
                '}';
    }
}
