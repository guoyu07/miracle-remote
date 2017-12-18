package com.miracle.framework.remote.netty.client;

import com.miracle.framework.remote.client.ClientException;
import com.miracle.framework.remote.exchange.Request;
import com.miracle.framework.remote.exchange.Response;
import com.miracle.framework.remote.server.Server;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import static java.util.concurrent.TimeUnit.SECONDS;

@Sharable
public final class NettyClientDispatchHandler extends SimpleChannelInboundHandler<Response> {
    
    private final ConcurrentHashMap<Long, BlockingQueue<Response>> responseMap = new ConcurrentHashMap<>();
    
    @Override
    public void write(final ChannelHandlerContext ctx, final Object msg, final ChannelPromise promise) throws Exception {
        if (msg instanceof Request) {
            Request request = (Request) msg;
            responseMap.putIfAbsent(request.getMessageId(), new LinkedBlockingQueue<Response>(1));
        }
        super.write(ctx, msg, promise);
    }
    
    @Override
    protected void messageReceived(final ChannelHandlerContext ctx, final Response response) throws Exception {
        BlockingQueue<Response> queue = responseMap.get(response.getMessageId());
        queue.add(response);
    }
    
    public Response getResponse(final long messageId) {
        Response result;
        responseMap.putIfAbsent(messageId, new LinkedBlockingQueue<Response>(1));
        try {
            result = responseMap.get(messageId).take();
            if (null == result) {
                result = getSystemMessage();
            }
        } catch (final InterruptedException ex) {
            throw new ClientException(ex);
        } finally {
            responseMap.remove(messageId);
        }
        return result;
    }
    
    private Response getSystemMessage() {
        try {
            return responseMap.get(Server.SYSTEM_MESSAGE_ID).poll(5, SECONDS);
        } catch (final InterruptedException ex) {
            throw new ClientException(ex);
        }
    }
}
