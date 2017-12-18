package com.miracle.framework.remote.netty.server;

import com.miracle.framework.remote.netty.codec.SerializeType;
import com.miracle.framework.remote.server.Server;
import com.miracle.framework.remote.server.ServerException;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class NettyServer implements Server {
    
    private final int bossGroupThreads;
    
    private final int workerGroupThreads;
    
    private final int backlogSize;
    
    private final SerializeType serializeType;
    
    private Channel channel;
    
    private EventLoopGroup bossGroup;
    
    private EventLoopGroup workerGroup;
    
    @Override
    public void start(final int port) {
        bossGroup = new NioEventLoopGroup(bossGroupThreads);
        workerGroup = new NioEventLoopGroup(workerGroupThreads);
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        try {
        serverBootstrap
            .group(bossGroup, workerGroup)
            .channel(NioServerSocketChannel.class)
            .option(ChannelOption.SO_BACKLOG, backlogSize)
            .childOption(ChannelOption.SO_KEEPALIVE, true)
            .childOption(ChannelOption.TCP_NODELAY, true)
            .childHandler(serializeType.getServerChannelInitializer().newInstance());
            channel = serverBootstrap.bind(port).sync().channel();
        } catch (final Exception ex) {
            throw new ServerException(Server.SYSTEM_MESSAGE_ID, ex);
        }
    }
    
    @Override
    public void stop() {
        if (null != channel) {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            channel.closeFuture().syncUninterruptibly();
        }
    }
}
