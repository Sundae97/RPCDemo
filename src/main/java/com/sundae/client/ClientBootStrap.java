package com.sundae.client;

import com.sundae.AbstractBootStrap;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * ClientBootstarp
 *
 * @author daijiyuan
 * @date 2020/1/14
 * @comment
 */
public class ClientBootStrap extends AbstractBootStrap {

    private Bootstrap clientBootstarp = new Bootstrap();
    private static final String REMOTE_HOST = "localhost";
    private static final int PORT = 8899;
    private EventLoopGroup workerLoopGroup = new NioEventLoopGroup();
    public static TestSendHandler testSendHandler = new TestSendHandler();

    private static final ClientBootStrap singleton = new ClientBootStrap();

    public static ClientBootStrap getSingleton() {
        return singleton;
    }

    public void bootstrap(){
        try{
            clientBootstarp.group(workerLoopGroup)
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(getBuiltInHandlers());
                            socketChannel.pipeline().addLast(getCustomHandlers());
                        }
                    })
                    .option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            ChannelFuture channelFuture = clientBootstarp.connect(REMOTE_HOST, PORT).sync();
            Config.channel = channelFuture.channel();
            channelFuture.channel().closeFuture().sync();       //TODO Test 统一存放Channel
            System.out.println("close channel");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
//            workerLoopGroup.shutdownGracefully();
        }
    }

    public ChannelFuture connect(String inetHost, int inetPort){
        return clientBootstarp.connect(inetHost, inetPort);
    }

    protected ChannelHandler[] getBuiltInHandlers() {
        return new ChannelHandler[]{
//                new ByteArrayDecoder(),
//                new ByteArrayEncoder(),
        };
    }

    protected ChannelHandler[] getCustomHandlers() {
        return new ChannelHandler[]{
                new RequestEncoder(),
                new ResponseDecoder(),
                testSendHandler
        };
    }
}
