package com.sundae.service;

import com.sundae.ProtocolData;
import com.sundae.client.ClientBootStrap;
import com.sundae.client.Config;
import com.sundae.server.Response;
import com.sundae.util.KryoUtil;
import com.sundae.util.ReflectUtil;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelPromise;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * ServiceInvokeHandler
 *
 * @author daijiyuan
 * @date 2020/1/15
 * @comment
 */
public class ServiceInvokeHandler implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("invoke ---> " + method.toGenericString());
        ServiceRemoteInvokeBean remoteInvokeBean = new ServiceRemoteInvokeBean();
        remoteInvokeBean.setMethodDescription(ReflectUtil.getMethodDescription(method));
        remoteInvokeBean.setArgs(args);
        byte[] encodeBytes = KryoUtil.doSerialize(remoteInvokeBean);
        ProtocolData protocolData = new ProtocolData("1", encodeBytes);
        ChannelPromise channelPromise = ClientBootStrap.testSendHandler.sendMessage(protocolData);
        Response response;
        try{
            channelPromise.await(2, TimeUnit.SECONDS);      //相当于设置一个阻塞超时
            response = ClientBootStrap.testSendHandler.getResponse();
            System.out.println();
            System.out.println("---------------------------------------------------------------");
            System.out.println("source method name : " + method.toGenericString());
            System.out.println("result method name : " + response.getResultType());
            System.out.println("---------------------------------------------------------------");
            System.out.println();
        }catch (InterruptedException e){
            System.out.println(e.getMessage());
            return null;
        }
        return response.getResult();
    }
}
