package com.miracle.framework.remote.netty.codec;

import com.miracle.framework.lang.serialize.kryo.KryoSerialization;
import com.miracle.framework.lang.serialize.kryo.KryoFactory;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

import java.io.IOException;

public final class KryoPool {
    
    private static final byte[] LENGTH_PLACEHOLDER = new byte[4];
    
    private final KryoFactory kryoFactory = new KryoFactory();
    
    public void encode(final ByteBuf out, final Object message) throws IOException {
        ByteBufOutputStream output = new ByteBufOutputStream(out);
        output.write(LENGTH_PLACEHOLDER);
        new KryoSerialization(kryoFactory).serialize(output, message);
    }
    
    public Object decode(final ByteBuf in) throws IOException {
        KryoSerialization kryoSerialization = new KryoSerialization(kryoFactory);
        return kryoSerialization.deserialize(new ByteBufInputStream(in));
    }
}
