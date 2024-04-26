package com.fleming.weatherApp;

import static org.junit.jupiter.api.Assertions.*;

class WeatherAppMainTest
{
    // testing the validateInput method for a valid input length and format
    @org.junit.jupiter.api.Test
    void validDataLength()
    {
        System.out.println("\nValid Data Test: input is 12345...");
        WeatherWrapper wrapper = new WeatherWrapper();
        wrapper.validateInput("12345");
        assertTrue(wrapper.isValid());
    }

    // testing the validateInput method for an invalid input length but valid format
    @org.junit.jupiter.api.Test
    void invalidDataLength()
    {
        System.out.println("\nInvalid Data Test: input is 1234...");
        WeatherWrapper wrapper = new WeatherWrapper();
        wrapper.validateInput("1234");
        assertFalse(wrapper.isValid());
    }

    // testing the validateInput method for a valid input length but invalid format
    @org.junit.jupiter.api.Test
    void invalidDataFormat()
    {
        System.out.println("\nInvalid Data Test: input is abcde...");
        WeatherWrapper wrapper = new WeatherWrapper();
        wrapper.validateInput("abcde");
        assertFalse(wrapper.isValid());
    }

    // testing the makeConnection method for a valid connection
    @org.junit.jupiter.api.Test
    void validConnection()
    {
        System.out.println("\nValid Connection Test: input is 12345...");
        WeatherWrapper wrapper = new WeatherWrapper();
        wrapper.validateInput("12345");
        wrapper.makeConnection("12345");
        assertTrue(wrapper.isConnected());
    }

    // testing the makeConnection method for an invalid connection
    @org.junit.jupiter.api.Test
    void invalidConnection()
    {
        System.out.println("\nInvalid Connection Test: input is 1234...");
        WeatherWrapper wrapper = new WeatherWrapper();
        wrapper.validateInput("1234");
        wrapper.makeConnection("1234");
        assertFalse(wrapper.isConnected());
    }
}