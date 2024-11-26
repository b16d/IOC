package com.acl.service;

import com.acl.exception.BadConstructor;
import com.acl.exception.CyclicDependency;
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
                    createObject(c, new ArrayList<>());
                } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    void createObject(Class<?> classToCreate, List<String> parentObjects) throws InvocationTargetException, InstantiationException, IllegalAccessException {

        if (parentObjects.contains(classToCreate.getName())) {
            throw new CyclicDependency("Cyclic dependency check parents for this class: " + classToCreate);
        }
        if (classToCreate.getDeclaredFields().length == 0) {
            Constructor<?> constructor = classToCreate.getDeclaredConstructors()[0];
            Object o = constructor.newInstance();

            objectMapName.put(o.getClass().getName(), o);
        } else {

            Constructor<?> ct = classToCreate.getDeclaredConstructors()[0];

            List<Object> listOfParameters = new ArrayList<>();
            for (Class<?> cl : ct.getParameterTypes()) {
                if (objectMapName.get(cl.getName()) == null) { // case when object is not found we need to create it
                    parentObjects.add(classToCreate.getName());
                    createObject(cl, parentObjects);
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

    public Object getBean(String className) {
        return objectMapName.get(className);
    }
}
