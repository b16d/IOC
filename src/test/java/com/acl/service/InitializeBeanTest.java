package com.acl.service;

import com.acl.exception.BadConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class InitializeBeanTest {

    @Test
    public void should_throw_an_exception_if_too_many_constructor() throws ClassNotFoundException {
        String packageName = "com.acl.bad.data";
        InitializeBean initializeBean = new InitializeBean(List.of(packageName));

        Assertions.assertThrows(BadConstructor.class, initializeBean::createBean);
    }

    @Test
    public void should_test() throws ClassNotFoundException {
        String packageName = "com.acl.data";
        InitializeBean initializeBean = new InitializeBean(List.of(packageName));
        initializeBean.createBean();
        Assertions.assertEquals(4, initializeBean.getObjectMapName().size());
    }
}
