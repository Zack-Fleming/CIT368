package com.fleming.assignemnt1;

import com.google.gson.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Pattern;

public class WeatherAppMain
{
    private static final String URL_BASE = "http://api.weatherapi.com/v1/forecast.json?key=f0fbfab6e3204ef8b70183748232701&q=",
                                URL_END = "&days=3&aqi=no&alerts=no";

    public static void main(String[] args)
    {
        try{
            Scanner input = new Scanner(System.in);                                     // scanner object for input

            Pattern pattern = Pattern.compile("\\d{5}");                          // regex for input matching

            boolean cont = true;                                                        // loop until user exits
            while(cont)
            {
                System.out.print("enter a US zipcode: ");                               // get the zipcode input
                String zipcode = input.nextLine();

                if (pattern.matcher(zipcode).matches())                                 // check if input is in proper format
                {
                    URL api = new URL(URL_BASE + zipcode + URL_END);              // create the urk to connect to
                    HttpURLConnection conn = (HttpURLConnection) api.openConnection();

                    conn.setRequestMethod("GET");                                       // set request type and connect
                    conn.connect();

                    int resCode = conn.getResponseCode();                               // get the response code and setup data storage
                    StringBuilder allTheData = new StringBuilder();
                    Scanner reader;

                    // parse response code
                    if (resCode == 400) reader = new Scanner(conn.getErrorStream());
                    else if (resCode == 200) reader = new Scanner(conn.getInputStream());
                    else
                    {
                        System.out.println("shit's broken....");
                        return;
                    }

                    while (reader.hasNext()) allTheData.append(reader.nextLine());      // read response data
                    reader.close();

                    Gson gson = new GsonBuilder().setPrettyPrinting().create();         // create the JSON parser
                    JsonObject root = new JsonParser().parse(allTheData.toString()).getAsJsonObject();

                    if (root.has("error"))                                  // if JSON has error tag, print it
                        System.out.println(gson.toJson(root));
                    else                                                                // no error, print the forecast
                    {
                        JsonObject  loc = root.getAsJsonObject("location"); // get some base information
                        JsonElement name = loc.get("name"),
                                    region = loc.get("region"),
                                    country = loc.get("country");

                        System.out.println("weather for: " + name.getAsString() + " " + region.getAsString() + ", " + country.getAsString());
                        //System.out.println(gson.toJson(root));

                        JsonObject forecast = root.getAsJsonObject("forecast"); // get the corecast data
                        JsonArray days = forecast.getAsJsonArray("forecastday");

                        for (JsonElement day : days)                                        // loop through the days
                        {
                            JsonObject temp = day.getAsJsonObject();
                            System.out.println("\tOn the day of " + temp.get("date").getAsString() + ":");

                            JsonObject data = temp.getAsJsonObject("day");
                            System.out.println(
                                    "\t\tCondition: " + data.getAsJsonObject("condition").get("text").getAsString() + "\n" +
                                    "\t\tHigh temp: " + data.get("maxtemp_f").getAsString() + "°F\n" +
                                    "\t\tLow temp : " + data.get("mintemp_f").getAsString() + "°F\n"
                            );
                        }
                    }
                }
                else System.out.println("incorrect zipcode input....");                     // the zipcode was not correct

                System.out.print("\ncontinue with another city (y/n): ");                   // ask if the user wants to continue
                String choice = input.nextLine().toLowerCase();

                if (choice.equals("n") || choice.equals("no")) cont = false;                // exit if chosen
            }
        }
        catch (IOException e) { throw new RuntimeException(e); }
    }
}