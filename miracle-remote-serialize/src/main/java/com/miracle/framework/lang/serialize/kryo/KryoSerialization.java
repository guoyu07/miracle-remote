package com.miracle.framework.lang.serialize.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.miracle.framework.lang.serialize.Serialization;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@RequiredArgsConstructor
public final class KryoSerialization implements Serialization {
    
    private final KryoFactory kryoFactory;
    
    @Override
    public void serialize(final OutputStream out, final Object message) throws IOException {
        Kryo kryo = kryoFactory.getKryo();
        Output output = new Output(out);
        kryo.writeClassAndObject(output, message);
        output.close();
        kryoFactory.returnKryo(kryo);
    }
    
    @Override
    public Object deserialize(final InputStream in) throws IOException {
        Kryo kryo = kryoFactory.getKryo();
        Input input = new Input(in);
        Object result = kryo.readClassAndObject(input);
        input.close();
        kryoFactory.returnKryo(kryo);
        return result;
    }
}
