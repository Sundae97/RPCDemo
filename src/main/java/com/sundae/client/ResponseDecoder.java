package com.sundae.client;

import com.sundae.server.Response;
import com.sundae.util.KryoUtil;
import com.sundae.util.ProtocolUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * ResponseDecode
 *
 * @author daijiyuan
 * @date 2020/1/14
 * @comment
 */
public class ResponseDecoder extends ByteToMessageDecoder {
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        byte[] msg = ProtocolUtil.extractProtocolData(byteBuf);
        if(msg == null) return;
        Response response = KryoUtil.doDeserialize(msg, Response.class);
        System.out.printf("{resultType = %s , result = %s , throwable = %s\n",
                response.getResultType(),
                response.getResult(),
                response.getException()
        );
        channelHandlerContext.fireChannelRead(response);
    }
}
