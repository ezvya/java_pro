package java_pro.homework_1;

import java_pro.homework_1.annotations.AfterSuite;
import java_pro.homework_1.annotations.BeforeSuite;
import java_pro.homework_1.annotations.Test;

public class AnnotationTests {

    @BeforeSuite
    public static void setUp() {
        System.out.println("Before suite setup");
        System.out.println("==================");
    }

    @Test(priority = 10)
    public void testA() {
        System.out.println("Running A with priority {10}");
    }

    @Test(priority = 1)
    public void testB() {
        System.out.println("Running B with priority {1}");
    }

    @Test // default priority = 5
    public void testC() {
        System.out.println("Running C with priority {5}");
    }

    @AfterSuite
    public static void tearDown() {
        System.out.println("====================");
        System.out.println("After suite teardown");
    }

}
