package com.sundae.client;

import com.sundae.ProtocolData;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * RequestEncoder
 *
 * @author daijiyuan
 * @date 2020/1/14
 * @comment
 */
public class RequestEncoder extends MessageToByteEncoder<ProtocolData> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ProtocolData data, ByteBuf byteBuf) throws Exception {
//        byteBuf.writeByte((byte)2);       //TODO 前几个byte可以用来存储版本信息
//        byteBuf.writeByte((byte)10);
        byteBuf.writeInt(data.getBodyData().length);
        byteBuf.writeBytes(data.getBodyData());
        System.out.println("Client -- body length = " + data.getBodyData().length);
    }




}
