package com.acl.bad.data;

import com.acl.exception.BadConstructor;
import com.acl.ioc.annotation.MyService;

@MyService
public class BadConnector {

    public BadConnector() {

    }

    public BadConnector(String value) {

    }
}
