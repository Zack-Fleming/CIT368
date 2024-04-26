package com.fleming.weatherApp;

import static org.junit.jupiter.api.Assertions.*;

class WeatherAppMainTest
{

    @org.junit.jupiter.api.Test
    void validData()
    {
        System.out.println("Valid Data Test:");
        WeatherWrapper wrapper = new WeatherWrapper();
        wrapper.validateInput("12345");
        assertTrue(wrapper.isValid());
    }

    @org.junit.jupiter.api.Test
    void invalidData()
    {
        System.out.println("\nInvalid Data Test:");
        WeatherWrapper wrapper = new WeatherWrapper();
        wrapper.validateInput("1234");
        assertFalse(wrapper.isValid());
    }

    @org.junit.jupiter.api.Test
    void validConnection()
    {
        System.out.println("\nValid Connection Test:");
        WeatherWrapper wrapper = new WeatherWrapper();
        wrapper.validateInput("12345");
        wrapper.makeConnection("12345");
        assertTrue(wrapper.isConnected());
    }

    @org.junit.jupiter.api.Test
    void invalidConnection()
    {
        System.out.println("\nInvalid Connection Test:");
        WeatherWrapper wrapper = new WeatherWrapper();
        wrapper.validateInput("1234");
        wrapper.makeConnection("1234");
        assertFalse(wrapper.isConnected());
    }
}