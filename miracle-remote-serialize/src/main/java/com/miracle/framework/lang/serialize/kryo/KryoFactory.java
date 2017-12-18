package com.miracle.framework.lang.serialize.kryo;

import com.esotericsoftware.kryo.Kryo;
import org.apache.commons.pool2.impl.GenericObjectPool;

public final class KryoFactory {
    
    private final GenericObjectPool<Kryo> kryoPool;
    
    public KryoFactory() {
        kryoPool = new GenericObjectPool<>(new PooledKryoFactory());
    }
    
    public Kryo getKryo() {
        try {
            return kryoPool.borrowObject();
        } catch (final Exception ex) {
            throw new KryoPoolException(ex);
        }
    }
    
    public void returnKryo(final Kryo kryo) {
        kryoPool.returnObject(kryo);
    }
}
