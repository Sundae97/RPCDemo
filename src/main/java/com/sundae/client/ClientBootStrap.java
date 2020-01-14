package com.sundae.client;

import com.sundae.AbstractBootStrap;
import com.sundae.KryoUtil;
import com.sundae.ProtocolData;
import com.sundae.TestBean;
import io.netty.bootstrap.Bootstrap;
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

    private static final String REMOTE_HOST = "localhost";
    private static final int PORT = 8899;
    private EventLoopGroup workerLoopGroup = new NioEventLoopGroup();

    public void bootstrap(){
        try{
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerLoopGroup)
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(getBuiltInHandlers());
                            socketChannel.pipeline().addLast(getCustomHandlers());
                        }
                    })
                    .option(ChannelOption.TCP_NODELAY, true);
            ChannelFuture channelFuture = bootstrap.connect(REMOTE_HOST, PORT).sync();
            TestBean testBean = new TestBean();
            testBean.setId(6);
            testBean.setName("xiaoming");
            testBean.setAdmin(true);
            byte[] bytes = KryoUtil.doSerialize(testBean);
            TestBean t = KryoUtil.doDeserialize(bytes, TestBean.class);
            System.out.println(t.toString());
            System.out.println(new String(bytes));

            ProtocolData protocolData = new ProtocolData();
            protocolData.setBodyData(bytes);
            channelFuture.channel().writeAndFlush(protocolData);
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerLoopGroup.shutdownGracefully();
        }
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
                new TestSendHandler()
        };
    }
}
