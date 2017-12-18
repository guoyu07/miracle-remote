package com.miracle.framework.remote.netty.client;

import com.miracle.framework.remote.netty.codec.KryoDecoder;
import com.miracle.framework.remote.netty.codec.KryoEncoder;
import com.miracle.framework.remote.netty.codec.KryoPool;
import io.netty.channel.socket.SocketChannel;

public final class KryoNettyClientChannelInitializer extends NettyClientChannelInitializer {
    
    private final KryoPool kryoPool = new KryoPool();
    
    @Override
    protected void initChannel(final SocketChannel channel) throws Exception {
        channel.pipeline().addLast(new KryoEncoder(kryoPool));
        channel.pipeline().addLast(new KryoDecoder(kryoPool));
        super.initChannel(channel);
    }
}
