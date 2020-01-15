package com.sundae.server;

import com.sundae.TestMethod;
import com.sundae.TestMethodImpl;
import com.sundae.service.ServiceProvider;
import io.netty.bootstrap.ServerBootstrap;

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

        serverBootStrap.addServiceProvider(new ServiceProvider(TestMethod.class, new TestMethodImpl()));
        serverBootStrap.doBootStrap();

        //TODO 调用服务方法描述元数据 包名 类名 方法名 返回参数 传递参数
        //TODO 序列化
        //TODO 注解扫描
        //TODO 初始化扫描暴露的服务，创建Map ，创建线程池
        //TODO 调用方法
    }

}
