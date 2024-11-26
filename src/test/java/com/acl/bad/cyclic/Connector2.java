package com.acl.bad.cyclic;

import com.acl.ioc.annotation.MyService;

@MyService
public class Connector2 {
    private final Connector3 connector3;


    public Connector2(Connector3 connector3) {
        this.connector3 = connector3;
    }
}

