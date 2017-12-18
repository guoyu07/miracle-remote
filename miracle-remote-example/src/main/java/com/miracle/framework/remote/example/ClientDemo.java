package com.miracle.framework.remote.example;

import com.miracle.framework.remote.example.fixture.Foo;
import com.miracle.framework.remote.example.fixture.FooService;
import com.miracle.framework.remote.exporter.RpcExporter;
import com.miracle.framework.remote.netty.client.NettyClient;
import com.miracle.framework.remote.netty.codec.SerializeType;

import java.net.InetSocketAddress;
import java.util.Properties;

public final class ClientDemo {
    
    public static void main(String[] args) throws Throwable {
        Properties prop = new Properties();
        prop.load(ServerBootstrap.class.getClassLoader().getResourceAsStream("example.properties"));
        NettyClient nettyClient = new NettyClient(Integer.parseInt(prop.getProperty("client.worker.group.threads")), SerializeType.valueOf(prop.getProperty("serialize.type")));
        nettyClient.connect(new InetSocketAddress("localhost", Integer.parseInt(prop.getProperty("server.port"))));
        System.out.println("client connect server successful");
        
        RpcExporter rpcExporter = new RpcExporter(FooService.class);
        
        // use spring aop to do it in future
        System.out.println("query fast:" + ((Foo) rpcExporter.invoke(nettyClient, FooService.class.getMethod("query", String.class), new Object[] {"fast"})).getBar());
        
        // test throw exception
        rpcExporter.invoke(nettyClient, FooService.class.getMethod("queryWithException", String.class), new Object[] {"slow"});
    }
}
