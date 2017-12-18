package com.miracle.framework.remote.example;

import com.miracle.framework.remote.example.fixture.FooService;
import com.miracle.framework.remote.example.fixture.FooServiceImpl;
import com.miracle.framework.remote.netty.codec.SerializeType;
import com.miracle.framework.remote.netty.server.NettyServer;
import com.miracle.framework.remote.netty.server.ServiceRegistry;

import java.io.IOException;
import java.util.Properties;

public final class ServerBootstrap {
    
    public static void main(String[] args) throws IOException {
        Properties prop = new Properties();
        prop.load(ServerBootstrap.class.getClassLoader().getResourceAsStream("example.properties"));
        NettyServer server = new NettyServer(
                Integer.parseInt(prop.getProperty("server.boss.group.threads")),
                Integer.parseInt(prop.getProperty("server.worker.group.threads")),
                Integer.parseInt(prop.getProperty("server.backlog.size")),
                SerializeType.valueOf(prop.getProperty("serialize.type")));
        server.start(Integer.parseInt(prop.getProperty("server.port")));
        ServiceRegistry.getInstance().register(FooService.class, FooServiceImpl.class);
        System.out.println("server start successful with port : " + prop.getProperty("server.port"));
    }
}
