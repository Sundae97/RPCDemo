package com.sundae.server;

import com.sundae.provider.*;
import com.sundae.service.ServiceMethodProvider;
import com.sundae.util.NetUtil;

/**
 * Main
 *
 * @author daijiyuan
 * @date 2020/1/14
 * @comment
 */
public class ServerMain {

    public static void main(String[] args) {
        //TODO NETTY 通讯
        ServerBootStrap serverBootStrap = new ServerBootStrap();

        serverBootStrap.addServiceProvider(new ServiceMethodProvider(TestMethod.class, new TestMethodImpl()));
        serverBootStrap.addServiceProvider(new ServiceMethodProvider(TestMethod2.class, new TestMethodImpl2()));
        serverBootStrap.addServiceProvider(new ServiceMethodProvider(TestMethod3.class, new TestMethodImpl3()));
        serverBootStrap.doBootStrap();
//        System.out.println(TestMethod.class.getCanonicalName());
        //TODO 调用服务方法描述元数据 包名 类名 方法名 返回参数 传递参数
        //TODO 序列化
        //TODO 注解扫描
        //TODO 初始化扫描暴露的服务，创建Map ，创建线程池
        //TODO 调用方法

        //TODO 服务提供者动态选择端口 可以单机部署多个服务提供者
        //TODO 负载均衡策略  --->  轮询 加权轮询 随机 一致性哈希 最小活跃数
        //TODO 多服务提供者，多zk注册中心
        //TODO 注册中心目录结构 /path/ClassCanonicalName/Version/ProviderInfo
    }

}
