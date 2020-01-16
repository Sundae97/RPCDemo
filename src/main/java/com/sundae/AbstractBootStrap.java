package com.sundae;

import com.sundae.registry.ZookeeperRegistryManager;
import com.sundae.util.NetUtil;
import io.netty.channel.ChannelHandler;

/**
 * AbstractBootStrap
 *
 * @author daijiyuan
 * @date 2020/1/14
 * @comment
 */
public abstract class AbstractBootStrap {

    private ZookeeperRegistryManager zookeeperRegistryManager;

    public void doBootStrap(){
        initConfig();
        init();
        bootstrap();
    }

    public void initConfig(){
        GlobalConfig.LOCAL_NET_ADDRESS = NetUtil.getLocalAddress();
    }

    protected void init(){
        zookeeperRegistryManager = new ZookeeperRegistryManager();
        zookeeperRegistryManager.connectZooKeeper(GlobalConfig.ZOOKEEPER_ADDRESS, GlobalConfig.ZOOKEEPER_PORT);
    }

    protected abstract void bootstrap();

    protected abstract ChannelHandler[] getBuiltInHandlers();

    protected abstract ChannelHandler[] getCustomHandlers();

}
