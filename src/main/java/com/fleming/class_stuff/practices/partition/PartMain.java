package com.fleming.class_stuff.practices.partition;

import java.util.Arrays;

public class PartMain
{
    public static void main(String[] args)
    {
        String[] input = PartFunctions.getInput();

        System.out.println("\nLen    : " + input.length);
        System.out.println("SUM    : " + PartFunctions.GetSum(input));
        System.out.println("PRODUCT: " + PartFunctions.GetProduct(input));
        System.out.println("Average: " + PartFunctions.GetAverage(input));
        System.out.println("Minimum: " + PartFunctions.GetMin(input));
        System.out.println("Maximum: " + PartFunctions.GetMax(input));

        System.out.println("\nBefore : " + Arrays.toString(input));
        System.out.println("After  : " + Arrays.toString(PartFunctions.ForwardSort(input)));

        System.out.println("\nBefore : " + Arrays.toString(input));
        System.out.println("After  : " + Arrays.toString(PartFunctions.ReverseSort(input)));
    }
}