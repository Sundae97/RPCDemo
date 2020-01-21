package com.sundae.server;

/**
 * ProviderInfo
 *
 * @author daijiyuan
 * @date 2020/1/21
 * @comment
 */
public class ProviderHostInfo {

    private String ip;
    private int port;
    private boolean usable;
    private int weight;

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

    public boolean isUsable() {
        return usable;
    }

    public void setUsable(boolean usable) {
        this.usable = usable;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "ProviderInfo{" +
                "inetHostIp='" + ip + '\'' +
                ", port=" + port +
                ", usable=" + usable +
                ", weight=" + weight +
                '}';
    }
}
