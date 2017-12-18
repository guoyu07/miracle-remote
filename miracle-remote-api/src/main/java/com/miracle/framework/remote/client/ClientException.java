package com.miracle.framework.remote.client;

public final class ClientException extends RuntimeException {
    
    private static final long serialVersionUID = 1400214981125931724L;
    
    public ClientException(final Exception cause) {
        super(cause);
    }
}
