package com.acl.service;

import com.acl.exception.BadConstructor;
import com.acl.ioc.annotation.MyService;
import com.acl.scanner.ClassScanner;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class InitializeBean {

    private final List<String> packageToScan;

    private final ClassScanner classScanner;
    private final Map<String, Object> objectMapName;


    public InitializeBean(List<String> packageToScan) {
        this.packageToScan  = packageToScan;

        this.classScanner   = new ClassScanner();
        this.objectMapName  = new HashMap<>();
    }

    void createBean() throws ClassNotFoundException {
        Set<? extends Class<?>> classSet = classScanner.findAllClassesWithAnnotation(packageToScan, MyService.class);

        classSet.forEach( c -> {
            if (c.getDeclaredConstructors().length != 1) {
                throw new BadConstructor("Too many constructors for class: " + getClass().getName() + " only one is expected");
            } else {
                try {
                    createObject(c);
                } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

    void createObject(Class<?> classToCreate) throws InvocationTargetException, InstantiationException, IllegalAccessException {

        if (classToCreate.getDeclaredFields().length == 0) {
            Constructor<?> constructor = classToCreate.getDeclaredConstructors()[0];
            Object o = constructor.newInstance();

            objectMapName.put(o.getClass().getName(), o);
        } else {

            Constructor<?> ct = classToCreate.getDeclaredConstructors()[0];

            List<Object> listOfParameters = new ArrayList<>();
            for (Class<?> cl : ct.getParameterTypes()) {
                if (objectMapName.get(cl.getName()) == null) { // case when object is not found we need to create it
                    createObject(cl);
                    listOfParameters.add(objectMapName.get(cl.getName()));

                } else {
                    listOfParameters.add(objectMapName.get(cl.getName()));
                }

            }

            //Create Object
            Object o = ct.newInstance(listOfParameters.toArray());
            objectMapName.put(o.getClass().getName(), o);
            System.out.println("Test: " + classToCreate);
        }
    }

    public Map<String, Object> getObjectMapName() {
        return objectMapName;
    }
}
