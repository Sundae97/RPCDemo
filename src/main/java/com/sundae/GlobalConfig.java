package com.sundae;

import com.sundae.service.ServiceBean;
import com.sundae.service.ServiceMethodProvider;

import java.nio.channels.Channel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * GobalConfig
 *
 * @author daijiyuan
 * @date 2020/1/15
 * @comment
 */
public class GlobalConfig {

    public static String LOCAL_NET_ADDRESS = null;

    public static String ZOOKEEPER_ADDRESS = "localhost";
    public static int ZOOKEEPER_PORT = 2181;

    //Provider
    public static final HashMap<String, ServiceBean> methodHashMap = new HashMap<>();
    public static final List<ServiceMethodProvider> providerList = new ArrayList<>();


    //Consumer
    public static final ConcurrentHashMap<String, Channel> providerMap = new ConcurrentHashMap<>();

}
