package com.acl.scanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FieldScanner extends Scanner {


    public Set<Class<?>> findAllAnnotatedField(List<String> packageList, Class<? extends Annotation> annotations) throws ClassNotFoundException {
        Set<Class<?>> classSet = new HashSet<>();
        packageList.forEach(p -> classSet.addAll(findAllClassesUsingClassLoader(p)));

        Set<Class<?>> annotatedFieldsClass = new HashSet<>();
        for (Class<?> classToCheck : classSet) {
            if (getFieldWithAnnotation(classToCheck, annotations)) {
                annotatedFieldsClass.add(classToCheck);
            }
        }
        return annotatedFieldsClass;
    }

    private boolean getFieldWithAnnotation(Class<?> classToCheck, Class<? extends Annotation> annotation) throws ClassNotFoundException {
        Class<?> clazz = ClassLoader.getSystemClassLoader()
                .loadClass(classToCheck.getName());
        Field[] fieldsTab = clazz.getDeclaredFields();
        Set<Class<?>> annotationSet = new HashSet<>();
        for (Field field : fieldsTab) {
            if (field.isAnnotationPresent(annotation)) {
                annotationSet.add(classToCheck);
            }
        }
        return  !annotationSet.isEmpty();
    }
}
