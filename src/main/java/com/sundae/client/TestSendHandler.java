package com.sundae.client;

import com.sundae.ProtocolData;
import com.sundae.server.Response;
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

    private ChannelHandlerContext channelHandlerContext;
    private ChannelPromise channelPromise;
    private Response response;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive");
        this.channelHandlerContext = ctx;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //System.out.println("channelRead --> " + ((Response)msg).toString());
        this.response = (Response) msg;
        channelPromise.setSuccess();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // super.exceptionCaught(ctx, cause);
        System.out.println("exceptionCaught ------> " + cause.toString());
    }

    public synchronized ChannelPromise sendMessage(ProtocolData protocolData){
        channelPromise = channelHandlerContext.newPromise();
        channelHandlerContext.writeAndFlush(protocolData);
        return channelPromise;
    }

    public Response getResponse() {
        return response;
    }
}
