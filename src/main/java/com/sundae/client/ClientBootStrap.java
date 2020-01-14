package com.sundae.client;

import com.sundae.AbstractBootStrap;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * ClientBootstarp
 *
 * @author daijiyuan
 * @date 2020/1/14
 * @comment
 */
public class ClientBootStrap extends AbstractBootStrap {

    private static final String REMOTE_HOST = "localhost";
    private static final int PORT = 8899;
    private EventLoopGroup workerLoopGroup = new NioEventLoopGroup();

    public void bootstrap(){
        try{
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerLoopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {

                        }
                    })
                    .option(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.connect(REMOTE_HOST, PORT);
        }finally {
            workerLoopGroup.shutdownGracefully();
        }
    }

    protected ChannelHandler[] getBuiltInHandlers() {
        return new ChannelHandler[]{

        };
    }

    protected ChannelHandler[] getCustomHandlers() {
        return new ChannelHandler[]{

        };
    }
}
