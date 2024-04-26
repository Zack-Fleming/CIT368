package com.fleming.weatherApp;

import com.google.gson.*;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class WeatherWrapper
{
    private boolean valid = false,
                    connected = false;
    private HttpURLConnection connection;
    private String rawData;


    public boolean isValid() { return valid; }
    public boolean isConnected() { return connected; }


    public WeatherWrapper() {}

    public void validateInput(String input)
    {
        valid = Security.validateInput(input);
        String message = valid ? "input valid..." : "input not valid...";
        System.out.println(message);
        if (valid) WeatherAppMain.LOG.getLogger().log(new LogRecord(Level.INFO, message));
        else WeatherAppMain.LOG.getLogger().log(new LogRecord(Level.WARNING, message));
    }

    public void makeConnection(String input)
    {
        if (valid)
        {
            try
            {
                URL url = URI.create(Secret.getConnectionString(input)).toURL();
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                connected = true;
                System.out.println("connection successful...");
                WeatherAppMain.LOG.getLogger().log(new LogRecord(Level.INFO, "Connection successful..."));
            }
            catch (Exception e) { WeatherAppMain.LOG.getLogger().log(new LogRecord(Level.SEVERE, "Connection unsuccessful...\n" + e.getLocalizedMessage())); }
        }
        else
        {
            System.out.println("connection unsuccessful...");
            WeatherAppMain.LOG.getLogger().log(new LogRecord(Level.WARNING, "connection unsuccessful..."));
        }
    }

    public void closeConnection()
    {
        if (connection != null) connection.disconnect();
        connected = false;
    }

    public void getRawResponseData()
    {
        try
        {
            StringBuilder temp = new StringBuilder();
            Scanner scanner;
            int responseCode = connection.getResponseCode();

            if (responseCode == 200) scanner = new Scanner(connection.getInputStream());
            else if (responseCode == 400) scanner = new Scanner(connection.getErrorStream());
            else throw new RuntimeException("connection unsuccessful...");

            while (scanner.hasNext()) temp.append(scanner.nextLine());

            scanner.close();
            rawData = temp.toString();
        }
        catch (Exception e) { WeatherAppMain.LOG.getLogger().log(new LogRecord(Level.SEVERE, e.getLocalizedMessage())); }
    }

    public void getFormattedData()
    {
        JsonObject root = JsonParser.parseString(rawData).getAsJsonObject();

        if (root.has("error"))
        {
            System.out.println("Error: " + root.getAsJsonObject("error").get("message").getAsString());
            WeatherAppMain.LOG.getLogger().log(new LogRecord(Level.WARNING, "Error: " + root.getAsJsonObject("error").get("message").getAsString()));
        }
        else
        {
            JsonObject  loc = root.getAsJsonObject("location");
            JsonElement name = loc.get("name"),
                        region = loc.get("region");
            JsonObject  forecast = root.getAsJsonObject("forecast");
            JsonArray   days = forecast.getAsJsonArray("forecastday");

            StringBuilder weather = new StringBuilder();
            weather.append("3 day forecast for: ").append(name.getAsString()).append(", ").append(region.getAsString()).append("\n");

            for (JsonElement day : days)
            {
                JsonObject temp = day.getAsJsonObject();
                weather.append("\ton the day of ").append(temp.get("date").getAsString()).append(":\n");
                JsonObject dayData = temp.getAsJsonObject("day");
                weather.append("\t\tCondition: ").append(dayData.getAsJsonObject("condition").get("text").getAsString()).append("\n");
                weather.append("\t\tHigh temp: ").append(dayData.get("maxtemp_f").getAsString()).append("°F\n");
                weather.append("\t\tLow temp : ").append(dayData.get("mintemp_f").getAsString()).append("°F\n");
            }

            System.out.println(weather);
            WeatherAppMain.LOG.getLogger().log(new LogRecord(Level.INFO,"\n" + weather));
        }
    }
}