package com.sundae.server;

import com.sundae.AbstractBootStrap;
import com.sundae.GlobalConfig;
import com.sundae.service.ServiceBean;
import com.sundae.service.ServiceProvider;
import com.sundae.util.ReflectUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.util.internal.StringUtil;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * NettyBootstrap
 *
 * @author daijiyuan
 * @date 2020/1/14
 * @comment
 */
public class ServerBootStrap extends AbstractBootStrap {

    private static final int PORT = 8899;

    private EventLoopGroup bossLoopGroup = new NioEventLoopGroup();
    private EventLoopGroup workerLoopGroup = new NioEventLoopGroup();

    protected void bootstrap() {
        try{
            io.netty.bootstrap.ServerBootstrap serverBootstrap = new io.netty.bootstrap.ServerBootstrap();
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

    public void addServiceProvider(ServiceProvider serviceProvider){
        Class interfaceClz = serviceProvider.getInterfaceClz();
        Method[] methods = interfaceClz.getDeclaredMethods();
        System.out.println(System.currentTimeMillis());
        for (Method m : methods) {
            String methodDescription = ReflectUtil.getMethodDescription(m);

            ServiceBean serviceBean = new ServiceBean();
            serviceBean.setInstance(serviceProvider.getImplObject());
            serviceBean.setMethod(m);
            serviceBean.setMethodDescription(methodDescription);

            System.out.println(methodDescription);

            GlobalConfig.methodHashMap.put(methodDescription, serviceBean);
        }
        System.out.println(System.currentTimeMillis());

    }

}
