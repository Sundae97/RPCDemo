package com.sundae.client;


import io.netty.channel.Channel;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Config
 *
 * @author daijiyuan
 * @date 2020/1/15
 * @comment
 */
public class Config {

    public static Channel channel;
    public static final ConcurrentHashMap<ServiceProvider, Set<Channel>> providerChannelsMap = new ConcurrentHashMap<>();

}
