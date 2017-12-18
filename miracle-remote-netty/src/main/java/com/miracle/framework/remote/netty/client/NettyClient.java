package com.miracle.framework.remote.netty.client;

import com.miracle.framework.remote.client.Client;
import com.miracle.framework.remote.client.ClientException;
import com.miracle.framework.remote.exchange.Request;
import com.miracle.framework.remote.exchange.Response;
import com.miracle.framework.remote.netty.codec.SerializeType;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

public final class NettyClient implements Client {
    
    private final int workerGroupThreads;
    
    private final NettyClientChannelInitializer clientChannelInitializer;
    
    private EventLoopGroup workerGroup;
    
    private Channel channel;
    
    public NettyClient(final int workerGroupThreads, final SerializeType serializeType) {
        this.workerGroupThreads = workerGroupThreads;
        try {
            clientChannelInitializer = serializeType.getClientChannelInitializer().newInstance();
        } catch (final ReflectiveOperationException ex) {
            throw new ClientException(ex);
        }
    }
    
    @Override
    public void connect(final InetSocketAddress socketAddress) {
        workerGroup = new NioEventLoopGroup(workerGroupThreads);
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap
                .group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(clientChannelInitializer);
        } catch (final Exception ex) {
            throw new ClientException(ex);
        }
        channel = bootstrap.connect(socketAddress.getAddress().getHostAddress(), socketAddress.getPort()).syncUninterruptibly().channel();
    }
    
    @Override
    public Response sent(final Request request) {
        channel.writeAndFlush(request);
        return clientChannelInitializer.getResponse(request.getMessageId());
    }
    
    @Override
    public InetSocketAddress getRemoteAddress() {
        SocketAddress remoteAddress = channel.remoteAddress();
        if (!(remoteAddress instanceof InetSocketAddress)) {
            throw new ClientException(new RuntimeException("Get remote address error, should be InetSocketAddress"));
        }
        return (InetSocketAddress) remoteAddress;
    }
    
    @Override
    public void close() {
        if (null != channel) {
            workerGroup.shutdownGracefully();
            channel.closeFuture().syncUninterruptibly();
        }
    }
}
