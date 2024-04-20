package com.fleming.class_stuff.practices.partition;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Scanner;

public class PartFunctions {
    public static String[] getInput() {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter a list of numbers (separated by spaces)\n: ");
        String input = in.nextLine();

        return input.split(" ");
    }

    public static BigInteger GetSum(String[] input) {
        BigInteger res = new BigInteger("0");
        for (String s : input) res = res.add(new BigInteger(s));

        return res;
    }

    public static BigInteger GetProduct(String[] input) {
        BigInteger res = new BigInteger("1");
        for (String s : input) res = res.multiply(new BigInteger(s));

        return res;
    }

    public static BigDecimal GetAverage(String[] input) {
        return new BigDecimal(GetSum(input)).divide(new BigDecimal(String.valueOf(input.length)), 20, RoundingMode.UNNECESSARY);
    }

    public static BigInteger GetMin(String[] input) {
        BigInteger MIN = new BigInteger(input[0]);
        for (String s : input) MIN = MIN.min(new BigInteger(s));

        return MIN;
    }

    public static BigInteger GetMax(String[] input)
    {
        BigInteger MAX = new BigInteger(input[0]);
        for (String s : input) MAX = MAX.max(new BigInteger(s));

        return MAX;
    }

    public static String[] ForwardSort(String[] input)
    {
        String[] res = input;
        for (int i = 0; i < res.length; i++)
        {
            BigInteger current = new BigInteger(res[i]);
            int j = i - 1;

            while(j >= 0 && (new BigInteger(res[j]).compareTo(current) > 0))
            {
                res[j + 1] = res[j];
                j--;
            }
            res[j + 1] = current.toString();
        }

        return res;
    }

    public static String[] ReverseSort(String[] input)
    {
        String[] res = input;
        for (int i = 0; i < res.length; i++)
        {
            BigInteger current = new BigInteger(res[i]);
            int j = i - 1;

            while(j >= 0 && (new BigInteger(res[j]).compareTo(current) < 0))
            {
                res[j + 1] = res[j];
                j--;
            }
            res[j + 1] = current.toString();
        }

        return res;
    }
}