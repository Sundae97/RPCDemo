package com.sundae.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 * ServiceProxyContext
 *
 * @author daijiyuan
 * @date 2020/1/20
 * @comment
 */
public class ServiceProxyContext {

    /**
     * 创建链路
     * @param serviceProvider
     * @return
     */
    public Channel createChannel(ServiceProvider serviceProvider){
        Collection<Channel> channelCollection = Config.providerChannelsMap.get(serviceProvider);
        if(channelCollection == null)
            channelCollection = new HashSet<>();
        //TODO　connect server and save channel
        ChannelFuture channelFuture = ClientBootStrap.getSingleton().connect(
                serviceProvider.getInetHost(),
                serviceProvider.getInetPort()
        );
        Channel channel = channelFuture.channel();
        channelCollection.add(channel);
        return channel;
    }

}
