package com.miracle.framework.remote.netty.codec;

import com.miracle.framework.remote.netty.client.JavaNettyClientChannelInitializer;
import com.miracle.framework.remote.netty.client.KryoNettyClientChannelInitializer;
import com.miracle.framework.remote.netty.client.NettyClientChannelInitializer;
import com.miracle.framework.remote.netty.server.JavaNettyServerChannelInitializer;
import com.miracle.framework.remote.netty.server.KryoNettyServerChannelInitializer;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum SerializeType {
    
    Java(JavaNettyServerChannelInitializer.class, JavaNettyClientChannelInitializer.class),
    Kryo(KryoNettyServerChannelInitializer.class, KryoNettyClientChannelInitializer.class);
    
    private final Class<? extends ChannelInitializer<SocketChannel>> serverChannelInitializer;
    
    private final Class<? extends NettyClientChannelInitializer> clientChannelInitializer;
}
