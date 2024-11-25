package com.acl;

import com.acl.ioc.annotation.MyService;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException {
        String packageName = "com.acl.connector";
        String packageName2 = "com.acl.service";

        Main main = new Main();

        Set<?> toTest = main.findAllClassesWithAnnotation(List.of(packageName), MyService.class);
        System.out.println("Hello world!");
        System.out.println("Begin Class contains annotation");
        toTest.forEach(System.out::println);
        System.out.println("End of Class contains annotation");


        Set<?> toTest2 = main.findAllAnnotatedField(List.of(packageName2), MyInject.class);
        System.out.println("\nBegin Class field annotation");
        toTest2.forEach(System.out::println);
        System.out.println("End of Class field annotation");

    }

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

    private Set<Class<?>> findAllClassesUsingClassLoader(String packageName) {
        InputStream stream = ClassLoader.getSystemClassLoader()
                .getResourceAsStream(packageName.replaceAll("[.]", "/"));

        if (stream == null) {
            return Collections.emptySet();
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        return reader.lines()
                .filter(line -> line.endsWith(".class"))
                .map(line -> getClass(line, packageName))
                .collect(Collectors.toSet());
    }

    private Class<?> getClass(String className, String packageName) {
        try {
            return Class.forName(packageName + "."
                    + className.substring(0, className.lastIndexOf('.')));
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found: " + e);
        }
        return null;
    }

    private boolean getClassWithAnnotation(Class<?> classToCheck, Class<? extends Annotation> annotation) throws ClassNotFoundException {
        Class<?> clazz = ClassLoader.getSystemClassLoader()
                .loadClass(classToCheck.getName());
        var classAnnotation = clazz.getAnnotation(annotation);

        return classAnnotation != null;
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