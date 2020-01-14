package com.sundae.server;

import com.sundae.ProtocolData;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * ResponseEncoder
 *
 * @author daijiyuan
 * @date 2020/1/14
 * @comment
 */
public class ResponseEncoder extends MessageToByteEncoder<ProtocolData> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ProtocolData protocolData, ByteBuf out) throws Exception {
        out.writeBytes(protocolData.getBodyData());
    }
}
