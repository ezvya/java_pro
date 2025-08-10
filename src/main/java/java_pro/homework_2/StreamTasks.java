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

        List<String> wordList = List.of("Carnivore", "Linux", "Equilibrium", "Oath", "Tool", "Mars Voltas");
        String words = "carnivore linux equilibrium oath tool mars voltas tool linux tool tool";

        //Найдите в списке целых чисел 3-е наибольшее число (пример: 5 10 9 4 3 10)
        System.out.printf("Third biggest non unique number: ====== %s ======%n", getThirdLargestNonUniqueInt(numbersSource));

        //Найдите в списке целых чисел 3-е наибольшее «уникальное» число
        System.out.printf("Third biggest unique number: ====== %s ======%n", getThirdLargestUniqueInt(numbersSource));

        // Имеется список объектов типа Сотрудник (имя, возраст, должность), необходимо получить список имен 3 самых
        // старших сотрудников с должностью «Инженер», в порядке убывания возраста
        System.out.println(getAllEngineersSortedByAge(employeesList));

        //  Имеется список объектов типа Сотрудник (имя, возраст, должность),
        //  посчитайте средний возраст сотрудников с должностью «Инженер»
        System.out.printf("Engineer's average age is: %s%n", countAvgEngineerAge(employeesList));

        //Найдите в списке слов самое длинное
        System.out.printf("The longest word is: %s%n", findTheLongestWord(wordList));

        //Имеется строка с набором слов в нижнем регистре, разделенных пробелом.
        //Постройте хеш-мапы, в которой будут храниться пары: слово - сколько раз оно встречается во входной строке
        System.out.println(countWords(words));

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

    private static List<String> getAllEngineersSortedByAge(List<Employee> employees) {
        return employees.stream()
                .filter(employee -> "Engineer".equalsIgnoreCase(employee.position()))
                .sorted(Comparator.comparingInt(Employee::age).reversed())
                .limit(3)
                .map(Employee::name)
                .collect(Collectors.toList());
    }

    private static double countAvgEngineerAge(List<Employee> employees) {
        return employees.stream()
                .filter(employee -> "Engineer".equals(employee.position()))
                .mapToDouble(Employee::age)
                .average()
                .orElse(0);
    }

    private static String findTheLongestWord(List<String> wordList) {
        return wordList.stream()
                .max(Comparator.comparingInt(String::length))
                .orElse(null);
    }

    private static Map<String, Integer> countWords(String words) {
        return Arrays.stream(words.split(" "))
                .collect(Collectors.groupingBy(
                        word -> word,
                        Collectors.summingInt(e -> 1)
                ));
    }
}
