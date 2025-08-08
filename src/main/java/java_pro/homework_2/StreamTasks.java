package java_pro.homework_2;

import java.util.*;
import java.util.stream.Collectors;

public class StreamTasks {

    public static void main(String[] args) {

        List<Integer> numbersSource = List.of(2, 3, 5, 7, 11, 11, 18);
        
        List<Employee> employeesList = List.of(
                new Employee("Bob", 43, "Engineer"),
                new Employee("Sarah", 41, "Secretary"),
                new Employee("Desmond", 46, "Maintenance"),
                new Employee("Barnie", 40, "Engineer"),
                new Employee("Rose", 37, "Engineer"),
                new Employee("Rob", 29, "Master Chief"),
                new Employee("Ernie", 37, "Engineer"),
                new Employee("Barb", 31, "Stuff Manager"),
                new Employee("Lance", 51, "Engineer")
        );

        //Найдите в списке целых чисел 3-е наибольшее число (пример: 5 10 9 4 3 10)
        System.out.printf("Third biggest non unique number: ====== %s ======%n", getThirdLargestNonUniqueInt(numbersSource));

        //Найдите в списке целых чисел 3-е наибольшее «уникальное» число
        System.out.printf("Third biggest unique number: ====== %s ======%n", getThirdLargestUniqueInt(numbersSource));

        // Имеется список объектов типа Сотрудник (имя, возраст, должность), необходимо получить список имен 3 самых
        // старших сотрудников с должностью «Инженер», в порядке убывания возраста
        System.out.println( getAllEngineersSortedByAge(employeesList));

    }


    private static int getThirdLargestNonUniqueInt(List<Integer> numbersSource) {

        return numbersSource.stream()
                .sorted(Comparator.reverseOrder())
                .skip(2)
                .mapToInt(Integer::intValue)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No such element exists!"));
    }

    private static int getThirdLargestUniqueInt(List<Integer> numbersSource) {

        return numbersSource.stream()
                .distinct()
                .sorted(Comparator.reverseOrder())
                .skip(2)
                .mapToInt(Integer::intValue)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No such element exists!"));
    }

    private static List<String> getAllEngineersSortedByAge(List<Employee> employeesList) {
        return employeesList.stream()
                .filter(employee -> "Engineer".equalsIgnoreCase(employee.position()))
                .sorted(Comparator.comparingInt(Employee::age).reversed())
                .limit(3)
                .map(Employee::name)
                .collect(Collectors.toList());
    }
}
