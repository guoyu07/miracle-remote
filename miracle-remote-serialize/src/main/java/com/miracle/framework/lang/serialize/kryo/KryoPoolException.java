package com.miracle.framework.lang.serialize.kryo;

public final class KryoPoolException extends RuntimeException {
    
    private static final long serialVersionUID = -2992257109597526961L;
    
    public KryoPoolException(final Exception cause) {
        super(cause);
    }
}
