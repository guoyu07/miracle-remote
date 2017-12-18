package com.miracle.framework.remote.netty.client;

import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public final class JavaNettyClientChannelInitializer extends NettyClientChannelInitializer {
    
    @Override
    protected void initChannel(final SocketChannel channel) throws Exception {
        channel.pipeline().addLast(new ObjectEncoder());
        channel.pipeline().addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(getClass().getClassLoader())));
        super.initChannel(channel);
    }
}
