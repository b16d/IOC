package com.acl.service;

import com.acl.connector.Connector;
import com.acl.ioc.annotation.MyInject;

public class Service {

    @MyInject
    private final Connector connector;

    public Service(Connector connector) {
        this.connector = connector;
    }
}
