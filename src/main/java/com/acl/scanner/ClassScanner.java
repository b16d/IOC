package com.acl.scanner;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClassScanner extends Scanner {

    public Set<Class<?>> findAllClassesWithAnnotation(List<String> packageList, Class<? extends Annotation> annotations) throws ClassNotFoundException {
        Set<Class<?>> classSet = new HashSet<>();
        packageList.forEach(p -> classSet.addAll(findAllClassesUsingClassLoader(p)));

        Set<Class<?>> serviceClass = new HashSet<>();
        for (Class<?> classToCheck : classSet) {
            if (getClassWithAnnotation(classToCheck, annotations)) {
                serviceClass.add(classToCheck);
            }
        }
        return serviceClass;
    }

    private boolean getClassWithAnnotation(Class<?> classToCheck, Class<? extends Annotation> annotation) throws ClassNotFoundException {
        Class<?> clazz = ClassLoader.getSystemClassLoader()
                .loadClass(classToCheck.getName());
        var classAnnotation = clazz.getAnnotation(annotation);

        return classAnnotation != null;
    }
}
