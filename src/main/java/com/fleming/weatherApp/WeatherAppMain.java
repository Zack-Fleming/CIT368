package com.fleming.weatherApp;

import com.fleming.weatherApp.log.Logging;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class WeatherAppMain
{
    public static final Logging LOG = new Logging();

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        WeatherWrapper wrapper = new WeatherWrapper();

        boolean run = true;
        while (run)
        {
            System.out.print("Enter a zip code: ");
            String input = scanner.nextLine();

            LOG.getLogger().log(new LogRecord(Level.INFO, "User entered zip code: " + input));

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

            System.out.print("Would you like to enter another zip code? (y/n): ");
            String response = scanner.nextLine();

            LOG.getLogger().log(new LogRecord(Level.INFO, "Would you like to enter another zip code? (y/n): "));
            LOG.getLogger().log(new LogRecord(Level.INFO, "User entered: " + response));

            if (response.equals("n"))
            {
                wrapper.closeConnection();
                run = wrapper.isConnected();

                LOG.printLogs();
            }
            else if (response.equals("y")) {}
            else
            {
                System.out.println("unrecognized input...\ndefaulting to continuing...\n");
                LOG.getLogger().log(new LogRecord(Level.WARNING, "Unrecognized input...\ndefaulting to continuing..."));
            }

            LOG.checkLines();
        }
    }
}