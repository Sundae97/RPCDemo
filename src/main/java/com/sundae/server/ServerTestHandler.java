package com.sundae.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * ServerTesthandler
 *
 * @author daijiyuan
 * @date 2020/1/14
 * @comment
 */
public class ServerTestHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("channelRead --> "+msg);
//        ctx.fireChannelActive();
    }
}
