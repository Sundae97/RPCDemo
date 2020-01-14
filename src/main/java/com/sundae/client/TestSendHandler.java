package com.sundae.client;

import com.sundae.KryoUtil;
import com.sundae.TestBean;
import io.netty.channel.*;

/**
 * TestSendHandler
 *
 * @author daijiyuan
 * @date 2020/1/14
 * @comment
 */
@ChannelHandler.Sharable
public class TestSendHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
        System.out.println("channelRead --> " + msg);
    }
}
