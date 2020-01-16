package com.sundae.server;

import com.esotericsoftware.kryo.Kryo;
import com.sundae.GlobalConfig;
import com.sundae.ProtocolData;
import com.sundae.service.ServiceRemoteInvokeBean;
import com.sundae.service.ServiceBean;
import com.sundae.util.KryoUtil;
import com.sundae.util.ProtocolUtil;
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

        //增加head解析，判断长度是否符合读取要求
        byte[] msg = ProtocolUtil.extractProtocolData(byteBuf);

        if(msg == null) return;

        ServiceRemoteInvokeBean remoteInvokeBean = KryoUtil.doDeserialize(msg, ServiceRemoteInvokeBean.class);
        System.out.println("server -- decode --> " + remoteInvokeBean.toString());
        ServiceBean serviceBean = GlobalConfig.methodHashMap.get(remoteInvokeBean.getMethodDescription());
        Method method = serviceBean.getMethod();
        Object instance = serviceBean.getInstance();
        Object[] args = remoteInvokeBean.getArgs();
        Object result = null;
        try{
            if(args == null)
                result = method.invoke(instance);
            else
                result = method.invoke(instance, args);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        Response response = new Response();
        response.setException(null);
        response.setResult(result);
        response.setResultType(method.getReturnType().getCanonicalName());
        System.out.println("response ---> " + response.toString());

        ProtocolData protocolData = new ProtocolData();
        protocolData.setVersion("1");
        protocolData.setBodyData(KryoUtil.doSerialize(response));
        channelHandlerContext.channel().writeAndFlush(protocolData);

//        ProtocolData protocolData = new ProtocolData();
//        protocolData.setVersion("1");
//        protocolData.setBodyData(KryoUtil.doSerialize(result));
//        channelHandlerContext.channel().writeAndFlush(protocolData);
    }


}
