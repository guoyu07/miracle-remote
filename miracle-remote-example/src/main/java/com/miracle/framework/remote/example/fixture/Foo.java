package com.miracle.framework.remote.example.fixture;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class Foo implements Serializable {
    
    private static final long serialVersionUID = 2213133522077991039L;
    
    private String bar;
}
