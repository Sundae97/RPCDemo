package com.sundae.server;

import com.sundae.GlobalConfig;
import com.sundae.ServiceRemoteInvokeBean;
import com.sundae.service.ServiceBean;
import com.sundae.util.KryoUtil;
import com.sundae.TestBean;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
//        byte first = byteBuf.readByte();
//        byte second = byteBuf.readByte();
//        System.out.println((int) first);
//        System.out.println((int) second);

//        byteBuf.skipBytes(2);       //跳过 相当于两次readByte()
        //TODO 以下为测试代码 等待删除
//        byte[] msg = new byte[byteBuf.readableBytes()];
//        byteBuf.readBytes(msg);
//        TestBean testBean = KryoUtil.doDeserialize(msg, TestBean.class);
//        System.out.println("TestBean ---> \n" + testBean.toString());
        //TODO 增加head解析，判断长度是否符合读取要求
        byte[] msg = extractProtocolData(byteBuf);

        if(msg == null) return;

        ServiceRemoteInvokeBean remoteInvokeBean = KryoUtil.doDeserialize(msg, ServiceRemoteInvokeBean.class);
        System.out.println("server -- decode --> " + remoteInvokeBean.toString());
        new Thread(()->{
            ServiceBean serviceBean = GlobalConfig.methodHashMap.get(remoteInvokeBean.getMethodDescription());
            Method method = serviceBean.getMethod();
            Object instance = serviceBean.getInstance();
            Object[] args = remoteInvokeBean.getArgs();
            try{
                if(args == null)
                    method.invoke(instance);
                else
                    method.invoke(instance, args);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public byte[] extractProtocolData(ByteBuf byteBuf){
        int headDataLength = 4;
        int protocolDataLength = byteBuf.readableBytes();
        //判断协议头长度
        if(protocolDataLength < headDataLength)
            return null;

        //不可以使用ReadInt 这个方法会移动游标,如果此次没有达到要求,下一次读取时就会出现问题
//        int bodyDataLength = byteBuf.readInt();
        //加几取决于协议中正文长度在协议头的什么位置
        int bodyDataLength = byteBuf.getInt(byteBuf.readerIndex() + 0);
        System.out.println("bodyDataLength ---> " + bodyDataLength);

        //如果长度小于协议的头长度和协议内正文的长度
        if(protocolDataLength < headDataLength + bodyDataLength)
            return null;

        byteBuf.readInt();      //移动游标 移到协议头后面的正文
        byte[] bodyData = new byte[bodyDataLength];
        byteBuf.readBytes(bodyData);
        return bodyData;
    }
}
