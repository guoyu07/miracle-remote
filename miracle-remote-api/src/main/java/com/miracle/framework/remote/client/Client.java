package com.miracle.framework.remote.client;

import com.miracle.framework.remote.exchange.Request;
import com.miracle.framework.remote.exchange.Response;

import java.net.InetSocketAddress;

public interface Client {
    
    void connect(InetSocketAddress socketAddress);
    
    Response sent(Request request);
    
    InetSocketAddress getRemoteAddress();
    
    void close();
}
