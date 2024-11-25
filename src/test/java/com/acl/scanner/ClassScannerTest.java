package com.acl.scanner;

import com.acl.ioc.annotation.MyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

public class ClassScannerTest {

    @Test
    public void test() throws ClassNotFoundException {
        String packageName = "com.acl.data";

        ClassScanner scanner = new ClassScanner();

        Set<?> toTest = scanner.findAllClassesWithAnnotation(List.of(packageName), MyService.class);
        Assertions.assertEquals(1, toTest.size());


        Class classTest = (Class) toTest.iterator().next();
       // classTest.getDeclaredConstructors().length
    }
}
