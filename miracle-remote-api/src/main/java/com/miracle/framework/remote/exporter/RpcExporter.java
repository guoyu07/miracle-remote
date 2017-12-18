package com.miracle.framework.remote.exporter;

import com.miracle.framework.remote.client.Client;
import com.miracle.framework.remote.exchange.Request;
import com.miracle.framework.remote.exchange.Response;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@RequiredArgsConstructor
public final class RpcExporter implements InvocationHandler {
    
    private final Class<?> target;
    
    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        Response response = ((Client) proxy).sent(new Request(target, method.getName(), args));
        if (null != response.getException()) {
            throw response.getException().getCause();
        }
        return response.getReturnValue();
    }
}
