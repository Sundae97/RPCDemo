package com.sundae;

import io.netty.channel.ChannelHandler;

/**
 * AbstractBootStrap
 *
 * @author daijiyuan
 * @date 2020/1/14
 * @comment
 */
public abstract class AbstractBootStrap {

    public void doBootStrap(){
        bootstrap();
    }

    protected abstract void bootstrap();

    protected abstract ChannelHandler[] getBuiltInHandlers();

    protected abstract ChannelHandler[] getCustomHandlers();

}
