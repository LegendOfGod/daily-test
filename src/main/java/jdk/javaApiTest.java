package jdk;

import abstarctTest.Son;

import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author lqb
 * @date 2022/2/28 10:06
 */
public class javaApiTest {
    public static void main(String[] args) {
        String hour = "1";
        //long minute = (long) (Long.parseLong(hour)*60);

        System.out.println(Double.parseDouble(hour) * 60);
        System.out.println(Math.ceil(Double.parseDouble(hour) * 60));
        //System.out.println(minute);
        //System.out.println(v);
    }
}
