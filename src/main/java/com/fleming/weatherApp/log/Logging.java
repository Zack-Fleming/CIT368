package com.fleming.weatherApp.log;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.logging.*;

public class Logging
{
    private final Logger logger;
    private FileHandler fileHandler;

    private final File ROOT = new File(System.getProperty("user.home") + File.separator + "Documents" + File.separator + "WeatherLogs");
    private String fileName, prevFileName;

    private int chunk;
    private final int MAX_LINES = 50;

    public Logging()
    {
        chunk = 0;
        logger = Logger.getLogger("WeatherAppMain");

        createLogFile(false);
    }

    public Logger getLogger() { return this.logger; }

    public void createLogFile(boolean cont)
    {
        try
        {
            if (!ROOT.exists()) ROOT.mkdirs();

            fileName = genFileName();

            fileHandler = new FileHandler(fileName);
            logger.addHandler(fileHandler);
            logger.setUseParentHandlers(false);
            LogFormatter formatter = new LogFormatter();
            fileHandler.setFormatter(formatter);
            if (cont) logger.info("Continued log from file: " + prevFileName);
            logger.log(new LogRecord(Level.INFO, "Log file created at " + LocalDateTime.now()));
            chunk++;
        }
        catch (Exception e) { throw new RuntimeException(e); }
    }

    private String genFileName()
    {
        while (true)
        {
            String s = ROOT.getPath() + File.separator + String.format("weatherAppLog-%s-%d.log", LocalDate.now(), chunk);
            File f = new File(s);
            if (!f.exists()) return s;
            else chunk++;
        }
    }

    public void checkLines()
    {
        try {
            int lines = 0;

            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            while (reader.readLine() != null) lines++;

            if (lines >= MAX_LINES)
            {
                fileHandler.close();
                prevFileName = fileName;
                createLogFile(true);
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void printLogs()
    {
        File[] logs = ROOT.listFiles();
        if (logs != null)
        {
            for (File f : logs)
            {
                if (f.getName().contains(".log") && !f.getName().contains(".lck"))
                {
                    try
                    {
                        BufferedReader reader = new BufferedReader(new FileReader(f));
                        String line;
                        while ((line = reader.readLine()) != null) System.out.println(line);
                        System.out.println("\n\n\n");
                        reader.close();
                    }
                    catch (IOException e) { throw new RuntimeException(e); }
                }
            }
        }
    }
}