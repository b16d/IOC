package com.acl.bad.cyclic;

import com.acl.ioc.annotation.MyService;

@MyService
public class Connector1 {

    private final Connector2 connector2;


    public Connector1(Connector2 connector2) {
        this.connector2 = connector2;
    }
}

