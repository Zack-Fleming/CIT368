package com.fleming.weatherApp;

import java.util.Scanner;

public class WeatherAppMain
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        WeatherWrapper wrapper = new WeatherWrapper();

        boolean run = true;
        while (run)
        {
            System.out.print("Enter a zip code: ");
            String input = scanner.nextLine();

            wrapper.validateInput(input);

            if (wrapper.isValid())
            {
                wrapper.makeConnection(input);
                if (wrapper.isConnected())
                {
                    wrapper.getRawResponseData();
                    wrapper.getFormattedData();
                }
            }

            System.out.print("Would you like to enter another zip code? (y/n)");
            String response = scanner.nextLine();
            if (response.equals("n"))
            {
                wrapper.closeConnection();
                run = wrapper.isConnected();
            }
        }
    }
}