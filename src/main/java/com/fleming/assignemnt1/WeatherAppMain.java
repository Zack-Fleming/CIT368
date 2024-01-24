package com.fleming.assignemnt1;

import java.util.Scanner;
import java.util.regex.Pattern;

public class WeatherAppMain
{
    private static final String URL_BASE = "http://api.weatherapi.com/v1/current.json?key=f0fbfab6e3204ef8b70183748232701&q=",
                                URL_END = "&aqi=no";

    public static void main(String[] args)
    {
        // create scanner for input
        Scanner input = new Scanner(System.in);

        //regex for input matching
        Pattern pattern = Pattern.compile("\\d{5}");

        // loop, until user exits
        boolean cont = true;
        while(cont)
        {

        }
    }
}