package com.sundae.util;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;

/**
 * NetUtil
 *
 * @author daijiyuan
 * @date 2020/1/16
 * @comment
 */
public class NetUtil {

    public static String getLocalAddress() {
        try {
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                if (netInterface.isLoopback() || netInterface.isVirtual() || !netInterface.isUp() || netInterface.getDisplayName().contains("Virtual")) {
                    continue;
                } else {
                    Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        ip = addresses.nextElement();
                        if (ip != null && ip instanceof Inet4Address) {
                            return ip.getHostAddress();
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("IP地址获取失败" + e.toString());
            //TODO Logger
        }
        return "";
    }

    /**
     * 选出一个可以使用的端口
     * @param port
     * @return
     */
    public static int selectUsablePort(int port){
        if(port < 10000)
            port = 10000;

        for (int selectedPort = port; selectedPort < 65535; selectedPort++) {
            if(!isLocalPortUsing(selectedPort)){
                return selectedPort;
            }
        }
        return 0;
    }

    /**
     * 测试本机端口是否被使用
     * @param port
     * @return
     */
    public static boolean isLocalPortUsing(int port){
        try(ServerSocket serverSocket = new ServerSocket(port)){    //try resource
            return false;
        } catch (IOException e) {
//            e.printStackTrace();
            return true;
        }
    }

}
