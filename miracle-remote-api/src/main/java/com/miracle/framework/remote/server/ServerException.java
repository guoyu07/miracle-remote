package com.miracle.framework.remote.server;

import lombok.Getter;

@Getter
public final class ServerException extends RuntimeException {
    
    private static final long serialVersionUID = 5438288073708201395L;
    
    private final long messageId;
    
    public ServerException(final long messageId, final Exception cause) {
        super(cause);
        this.messageId = messageId;
    }
}
