package com.drozda.battlecity.appflow;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

/**
 * Created by GFH on 17.11.2015.
 */
public class ComparatorTest {
    public static void main(String[] args) {
        List<Integer> list = asList(100, 90, 80, 0, 0, 0, 0, 0, 0, 0, 0, 0);

        List<Integer> list1 = list.stream().sorted().collect(Collectors.toList());

        System.out.println(list1);

    }
}
