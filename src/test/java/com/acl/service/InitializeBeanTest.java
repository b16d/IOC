package com.acl.service;

import com.acl.exception.BadConstructor;
import com.acl.exception.CyclicDependency;
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
    public void should_create_bean() throws ClassNotFoundException {
        String packageName = "com.acl.data";
        InitializeBean initializeBean = new InitializeBean(List.of(packageName));
        initializeBean.createBean();
        Assertions.assertEquals(4, initializeBean.getObjectMapName().size());
    }

    @Test
    public void should_found_cyclic_dependency() throws ClassNotFoundException {
        String packageName = "com.acl.bad.cyclic";
        InitializeBean initializeBean = new InitializeBean(List.of(packageName));

        Assertions.assertThrows(CyclicDependency.class, initializeBean::createBean);
    }
}
