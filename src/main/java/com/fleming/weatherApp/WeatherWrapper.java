package com.fleming.weatherApp;

import com.google.gson.*;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;

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
        if (Security.validateInput(input))
        {
            valid = true;
            System.out.println("input valid...");
        }
        else
        {
            valid = false;
            System.out.println("input invalid...");
        }
    }

    public void makeConnection(String input)
    {
        try
        {
            URL url = URI.create(Secret.getConnectionString(input)).toURL();
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            connected = true;
            System.out.println("connection successful...");
        }
        catch (Exception e) { throw new RuntimeException(e); }
    }

    public void closeConnection()
    {
        connection.disconnect();
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
        catch (Exception e) { throw new RuntimeException(e); }
    }

    public void getFormattedData()
    {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser parser = new JsonParser();
        JsonObject root = parser.parse(rawData).getAsJsonObject();

        if (root.has("error"))
            System.out.println("Error: " + root.getAsJsonObject("error").get("message").getAsString());
        else
        {
            JsonObject  loc = root.getAsJsonObject("location");
            JsonElement name = loc.get("name"),
                        region = loc.get("region");
            JsonObject  forecast = root.getAsJsonObject("forecast");
            JsonArray   days = forecast.getAsJsonArray("forecastday");

            StringBuilder weather = new StringBuilder();
            weather.append("3 day forecast for: " + name.getAsString() + ", " + region.getAsString() + "\n");

            for (JsonElement day : days)
            {
                JsonObject temp = day.getAsJsonObject();
                weather.append("\ton the day of " + temp.get("date").getAsString() + ":\n");
                JsonObject dayData = temp.getAsJsonObject("day");
                weather.append("\t\tCondition: " + dayData.getAsJsonObject("condition").get("text").getAsString() + "\n");
                weather.append("\t\tHigh temp: " + dayData.get("maxtemp_f").getAsString() + "\u00b0F\n");
                weather.append("\t\tLow temp : " + dayData.get("mintemp_f").getAsString() + "\u00b0F\n");
            }

            System.out.println(weather);
        }
    }
}