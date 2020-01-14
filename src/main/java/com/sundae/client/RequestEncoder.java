package com.sundae.client;

import com.sundae.KryoUtil;
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
        byteBuf.writeByte((byte)2);
        byteBuf.writeByte((byte)10);
        byteBuf.writeBytes(data.getBodyData());
    }
}
