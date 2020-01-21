package com.sundae.server;

import com.alibaba.fastjson.JSON;
import com.sundae.AbstractBootStrap;
import com.sundae.GlobalConfig;
import com.sundae.registry.ZookeeperRegistryManager;
import com.sundae.service.ServiceBean;
import com.sundae.service.ServiceMethodProvider;
import com.sundae.util.NetUtil;
import com.sundae.util.ReflectUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.lang.reflect.Method;

/**
 * NettyBootstrap
 *
 * @author daijiyuan
 * @date 2020/1/14
 * @comment
 */
public class ServerBootStrap extends AbstractBootStrap {

    private EventLoopGroup bossLoopGroup = new NioEventLoopGroup();
    private EventLoopGroup workerLoopGroup = new NioEventLoopGroup();

    protected void bootstrap() {
        //registry to zookeeper
        Config.LOCALHOST_IP = NetUtil.getLocalAddress();

        // start netty
        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossLoopGroup, workerLoopGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(getBuiltInHandlers());
                            socketChannel.pipeline().addLast(getCustomHandlers());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);

            Config.SELECTED_PORT = NetUtil.selectUsablePort(Config.DEFAULT_PORT);
            System.out.println("selected port -----> " + Config.SELECTED_PORT);
            ChannelFuture channelFuture = serverBootstrap.bind(Config.SELECTED_PORT).sync();
            System.out.println("service started");
            register2Zookeeper();
            System.out.println("register to zk");
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerLoopGroup.shutdownGracefully();
            bossLoopGroup.shutdownGracefully();
        }
    }

    protected ChannelHandler[] getBuiltInHandlers(){
        return new ChannelHandler[]{
//                new SslHandler(),
//                new HttpRequestEncoder(),
//                new HttpResponseEncoder(),
//                new HttpRequestDecoder(),
//                new HttpResponseDecoder(),
//                new HttpObjectAggregator(65535),
        };
    }

    protected ChannelHandler[] getCustomHandlers(){
        return new ChannelHandler[]{
                new RequestDecoder(),
                new ResponseEncoder(),
                new ServerTestHandler()
        };
    }

//    -------------------------------------------------------------------------
    public void addServiceProvider(ServiceMethodProvider serviceMethodProvider){
        Class interfaceClz = serviceMethodProvider.getInterfaceClz();
        Method[] methods = interfaceClz.getDeclaredMethods();
        System.out.println(System.currentTimeMillis());
        for (Method m : methods) {
            String methodDescription = ReflectUtil.getMethodDescription(m);

            ServiceBean serviceBean = new ServiceBean();
            serviceBean.setInstance(serviceMethodProvider.getImplObject());
            serviceBean.setMethod(m);
            serviceBean.setMethodDescription(methodDescription);

            System.out.println(methodDescription);

            GlobalConfig.methodHashMap.put(methodDescription, serviceBean);
        }
        GlobalConfig.providerList.add(serviceMethodProvider);
        System.out.println(System.currentTimeMillis());
    }


    public void register2Zookeeper(){
        ServerHostInfo serverHostInfo = new ServerHostInfo();
        serverHostInfo.setIp(Config.LOCALHOST_IP);
        serverHostInfo.setPort(Config.SELECTED_PORT);
        zookeeperRegistryManager.createNodeIfNotExist("/server/host/" + JSON.toJSONString(serverHostInfo), "");
        //公布HOST主机信息 方便服务消费者初始化时从zk上获取并且连接

        ProviderHostInfo providerHostInfo = new ProviderHostInfo();
        providerHostInfo.setPort(Config.SELECTED_PORT);
        providerHostInfo.setIp(Config.LOCALHOST_IP);
        providerHostInfo.setUsable(true);

        for (ServiceMethodProvider serviceMethodProvider : GlobalConfig.providerList){
            String clzName = serviceMethodProvider.getInterfaceClz().getCanonicalName();
            providerHostInfo.setWeight(serviceMethodProvider.getWeight());
            zookeeperRegistryManager.createNodeIfNotExist(
                    "/server/service/" + clzName + "/" + JSON.toJSONString(providerHostInfo),
                    ""
            );
        }
    }

//    -------------------------------------------------------------------------

}
