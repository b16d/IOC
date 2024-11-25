package com.acl.scanner;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

public class FieldScannerTest {

    @Test
    public void test() throws ClassNotFoundException {
        String packageName = "com.acl.data";

        FieldScanner scanner = new FieldScanner();

        Set<?> toTest = scanner.findAllAnnotatedField(List.of(packageName), MyInject.class);
        Assertions.assertEquals(1, toTest.size());
    }
}
