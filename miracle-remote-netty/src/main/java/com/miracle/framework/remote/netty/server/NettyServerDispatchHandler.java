package com.miracle.framework.remote.netty.server;

import com.miracle.framework.remote.exchange.Request;
import com.miracle.framework.remote.exchange.Response;
import com.miracle.framework.remote.server.Server;
import com.miracle.framework.remote.server.ServerException;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.lang.reflect.Method;

@Sharable
public class NettyServerDispatchHandler extends SimpleChannelInboundHandler<Request> {
    
    @Override
    protected void messageReceived(final ChannelHandlerContext ctx, final Request request) {
        Object returnValue = execute(request);
        ctx.writeAndFlush(new Response(request.getMessageId(), returnValue));
    }
    
    private Object execute(final Request request) {
        Object result;
        try {
            Object apiInstance = ServiceRegistry.getInstance().find(request.getApiClass()).newInstance();
            Method method = apiInstance.getClass().getMethod(request.getMethod(), getParameterTypes(request.getParameters()));
            result = method.invoke(apiInstance, request.getParameters());
        } catch (final Exception ex) {
            throw new ServerException(request.getMessageId(), ex);
        }
        return result;
    }
    
    private Class<?>[] getParameterTypes(final Object[] parameters) {
        Class<?>[] result = new Class<?>[parameters.length];
        int i = 0;
        for (Object each : parameters) {
            result[i] = each.getClass();
            i++;
        }
        return result;
    }
    
    @Override
    public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) throws Exception {
        Response response; 
        if (cause instanceof ServerException) {
            response = new Response(((ServerException) cause).getMessageId(), cause);
        } else {
            response = new Response(Server.SYSTEM_MESSAGE_ID, cause);
        }
        ctx.writeAndFlush(response);
    }
}
