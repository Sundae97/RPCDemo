package com.sundae;

import com.sundae.service.ServiceBean;

import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * GobalConfig
 *
 * @author daijiyuan
 * @date 2020/1/15
 * @comment
 */
public class GlobalConfig {

    public static final HashMap<String, ServiceBean> methodHashMap = new HashMap<>();

    public static String LOCAL_NET_ADDRESS = null;

    public static String ZOOKEEPER_ADDRESS = "localhost";
    public static int ZOOKEEPER_PORT = 2181;

}
