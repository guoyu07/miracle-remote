package com.miracle.framework.remote.example.fixture;

public interface FooService {
    
    Foo query(String bar) throws InterruptedException;
    
    Foo queryWithException(String bar);
}
