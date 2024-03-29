package com.fleming.class_stuff.practices.partition;

import java.util.Scanner;

/**
 * Apply security principles to this small program
 */
public class Partition {
	public static void main(String[] args) {
	    Scanner scanner = new Scanner(System.in);

	    System.out.println("Enter a list of numbers (separated by spaces): ");
	    String input = scanner.nextLine();

	    String[] numberStrings = input.split(" ");
	    int[] numbers = new int[numberStrings.length];
	    for (int i = 0; i < numberStrings.length; i++) {
	      numbers[i] = Integer.parseInt(numberStrings[i]);
	    }
	    
	    System.out.println("Process " + numberStrings.length + "");

	    int sum = 0;
	    for (int num : numbers) {
	      sum += num;
	    }
	    System.out.println("Sum: " + sum);
	    
	    int product = 1;
	    for (int num : numbers) {
	    	product *= num;
	    }
	    System.out.println("Product: " + product);

	    double average = sum / (double) numbers.length;
	    System.out.println("Average: " + average);

	    int min = Integer.MAX_VALUE;
	    int max = Integer.MIN_VALUE;
	    for (int num : numbers) {
	      if (num < min) {
	        min = num;
	      }
	      if (num > max) {
	        max = num;
	      }
	    }
	    System.out.println("Minimum: " + min);
	    System.out.println("Maximum: " + max);
		
	    System.out.println("Before: ");
	    for (int num : numbers) {
	    	System.out.println("[" + num + "]");
	    }
	    
	    for (int i = 1; i < numbers.length; i++) {
			int current = numbers[i];
			int j = i - 1;

			while (j >= 0 && numbers[j] > current) {
				numbers[j + 1] = numbers[j];
				j--;
			}
			
			//insert into position
			numbers[j + 1] = current;
		}
	    
	    System.out.println("After: ");
	    for (int num : numbers) {
	    	System.out.println("[" + num + "]");
	    }
	    
	    System.out.println("Before: ");
	    for (int num : numbers) {
	    	System.out.println("[" + num + "]");
	    }
	    
	    for (int i = 1; i < numbers.length; i++) {
			int current = numbers[i];
			int j = i - 1;

			while (j >= 0 && numbers[j] < current) {
				numbers[j + 1] = numbers[j];
				j--;
			}
			
			//insert into position
			numbers[j + 1] = current;
		}
	    
	    System.out.println("After: ");
	    for (int num : numbers) {
	    	System.out.println("[" + num + "]");
	    }
	}
}
