package com.sundae.server;

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

    private static final int PORT = 8899;

    private ZookeeperRegistryManager zookeeperRegistryManager = new ZookeeperRegistryManager();

    private EventLoopGroup bossLoopGroup = new NioEventLoopGroup();
    private EventLoopGroup workerLoopGroup = new NioEventLoopGroup();

    protected void bootstrap() {
        //registry to zookeeper
        register2Zookeeper();
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

            ChannelFuture channelFuture = serverBootstrap.bind(PORT).sync();
            System.out.println("service started");
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
        zookeeperRegistryManager.connectZooKeeper(GlobalConfig.ZOOKEEPER_ADDRESS, GlobalConfig.ZOOKEEPER_PORT);
        for (ServiceMethodProvider serviceMethodProvider : GlobalConfig.providerList){
            String clzName = serviceMethodProvider.getInterfaceClz().getCanonicalName();
            zookeeperRegistryManager.createNode(
                    "/simpleRPC/server/service/" + clzName + "/" + NetUtil.getLocalAddress() ,
                    ""
            );
            //TODO 节点信息需要添加端口
            System.out.println(clzName + " ---> registry to zk");
        }


    }

//    -------------------------------------------------------------------------

}
