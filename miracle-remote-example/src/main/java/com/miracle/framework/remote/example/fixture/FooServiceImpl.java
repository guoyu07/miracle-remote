package com.miracle.framework.remote.example.fixture;

public final class FooServiceImpl implements FooService {
    
    @Override
    public Foo query(final String bar) throws InterruptedException {
        if ("slow".equals(bar)) {
            Thread.sleep(300L);
        }
        return new Foo(bar);
    }
    
    @Override
    public Foo queryWithException(final String bar) {
        throw new RuntimeException("error occur");
    }
}
