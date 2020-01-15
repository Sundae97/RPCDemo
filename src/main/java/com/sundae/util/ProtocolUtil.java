package com.sundae.util;

import io.netty.buffer.ByteBuf;

/**
 * ProtocolUtil
 *
 * @author daijiyuan
 * @date 2020/1/15
 * @comment
 */
public class ProtocolUtil {

    public static byte[] extractProtocolData(ByteBuf byteBuf){
        int headDataLength = 4;     //自已定义的协议头长度 4个字节
        int protocolDataLength = byteBuf.readableBytes();      //获取当前缓冲区内可读的长度
        //判断协议头长度
        if(protocolDataLength < headDataLength)
            return null;

        //不可以使用ReadInt 这个方法会移动游标,如果此次没有达到要求,下一次读取时就会出现问题
        //        int bodyDataLength = byteBuf.readInt();

        //加几取决于协议中正文长度在协议头的什么位置
        int bodyDataLength = byteBuf.getInt(byteBuf.readerIndex() + 0);
        //TODO remove the line System.out.println("bodyDataLength ---> " + bodyDataLength);

        //如果长度小于协议的头长度和协议内正文的长度
        if(protocolDataLength < headDataLength + bodyDataLength)
            return null;

        //移动游标 移到协议头后面的正文
        byteBuf.readInt();
        byte[] bodyData = new byte[bodyDataLength];
        byteBuf.readBytes(bodyData);
        return bodyData;
    }

}
