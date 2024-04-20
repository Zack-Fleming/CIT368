package com.fleming.weatherApp;

public class Security
{
    public static boolean validateInput(String input)
    {
        return input.matches("^\\d{5}$");
    }
}