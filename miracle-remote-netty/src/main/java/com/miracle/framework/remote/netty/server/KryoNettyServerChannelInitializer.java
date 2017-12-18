package com.miracle.framework.remote.netty.server;

import com.miracle.framework.remote.netty.codec.KryoDecoder;
import com.miracle.framework.remote.netty.codec.KryoEncoder;
import com.miracle.framework.remote.netty.codec.KryoPool;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public final class KryoNettyServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    
    private final NettyServerDispatchHandler serverDispatchHandler = new NettyServerDispatchHandler();
    
    private final KryoPool kryoPool = new KryoPool();
    
    @Override
    protected void initChannel(final SocketChannel channel) throws Exception {
        channel.pipeline().addLast(new KryoEncoder(kryoPool));
        channel.pipeline().addLast(new KryoDecoder(kryoPool));
        channel.pipeline().addLast(serverDispatchHandler);
    }
}
