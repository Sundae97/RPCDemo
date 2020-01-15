package com.sundae.service;

import com.sundae.ProtocolData;
import com.sundae.ServiceRemoteInvokeBean;
import com.sundae.client.Config;
import com.sundae.util.KryoUtil;
import com.sundae.util.ReflectUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

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
        System.out.println(Config.channel.isActive());
        Config.channel.writeAndFlush(protocolData);
        return null;
    }
}
