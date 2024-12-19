package com.acl.bad.cyclic;

import com.acl.ioc.annotation.MyService;

@MyService
public class Connector3 {

    private final Connector1 connector1;

    public Connector3(Connector1 connector1) {
        this.connector1 = connector1;
    }
}

