package com.miracle.framework.remote.netty.client;

import com.miracle.framework.remote.exchange.Response;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class NettyClientChannelInitializer extends ChannelInitializer<SocketChannel> {
    
    private final NettyClientDispatchHandler clientDispatchHandler = new NettyClientDispatchHandler();
    
    @Override
    protected void initChannel(final SocketChannel channel) throws Exception {
        channel.pipeline().addLast(clientDispatchHandler);
    }
    
    public Response getResponse(final long messageId) {
        return clientDispatchHandler.getResponse(messageId);
    }
}
