package com.miracle.framework.remote.netty.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public final class JavaNettyServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    
    private final NettyServerDispatchHandler serverDispatchHandler = new NettyServerDispatchHandler();
    
    @Override
    protected void initChannel(final SocketChannel channel) throws Exception {
        channel.pipeline().addLast(new ObjectEncoder());
        channel.pipeline().addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(getClass().getClassLoader())));
        channel.pipeline().addLast(serverDispatchHandler);
    }
}
