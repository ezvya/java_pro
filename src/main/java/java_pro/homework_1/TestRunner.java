package java_pro.homework_1;

import java_pro.homework_1.annotations.AfterSuite;
import java_pro.homework_1.annotations.BeforeSuite;
import java_pro.homework_1.annotations.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TestRunner {

    public static void runTests(Class<?> clazz) {

        List<Method> testSuite = new ArrayList<>();

        Method beforeSuite = null;
        Method afterSuite = null;

        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(BeforeSuite.class)) {
                if (beforeSuite != null) {
                    throw new RuntimeException("@BeforeSuite can be declared only once!");
                }
                if (!Modifier.isStatic(method.getModifiers())) {
                    throw new RuntimeException("@BeforeSuite must be static");
                }
                beforeSuite = method;
            } else if (method.isAnnotationPresent(AfterSuite.class)) {
                if (afterSuite != null) {
                    throw new RuntimeException("@AfterSuite can be declared only once!");
                }
                if (!Modifier.isStatic(method.getModifiers())) {
                    throw new RuntimeException("@AfterSuite must be static");
                }
                afterSuite = method;
            } else if (method.isAnnotationPresent(Test.class)) {
                int priority = method.getAnnotation(Test.class).priority();
                if (priority < 1 || priority > 10) {
                    throw new IllegalArgumentException(
                            "Priority {%s} is out of bounds {1, 10}".formatted(priority));
                }
                testSuite.add(method);
            }
        }
        testSuite.sort(Comparator.comparingInt(m -> m.getAnnotation(Test.class).priority()));


        try {
            Object instance = clazz.getDeclaredConstructor().newInstance();

            if (beforeSuite != null) beforeSuite.invoke(null);
            for (Method test : testSuite) {
                test.invoke(instance);
            }
            if (afterSuite != null) afterSuite.invoke(null);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
