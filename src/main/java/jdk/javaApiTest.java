package jdk;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * @author lqb
 * @date 2022/2/28 10:06
 */
public class javaApiTest {
    public static void main(String[] args) {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("test");
        strings.add("test");
        strings.add("test1");
        ArrayList<String> collect = strings.stream()
                .collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(String::hashCode))), ArrayList::new));

        System.out.println(collect);
    }
}
