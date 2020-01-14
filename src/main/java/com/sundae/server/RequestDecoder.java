package com.sundae.server;

import com.sundae.KryoUtil;
import com.sundae.TestBean;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.CharsetUtil;

import java.util.List;

/**
 * RequestDecoder
 *
 * @author daijiyuan
 * @date 2020/1/14
 * @comment
 */
public class RequestDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        byte[] msg = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(msg);

        byte first = byteBuf.readByte();
        byte second = byteBuf.readByte();
        System.out.println((int) first);
        System.out.println((int) second);

//        byteBuf.skipBytes(2);       //跳过 相当于两次readByte()

//        byte[] msg = new byte[byteBuf.readableBytes()];
        TestBean testBean = KryoUtil.doDeserialize(msg, TestBean.class);
        System.out.println("TestBean ---> \n" + testBean.toString());
    }
}
