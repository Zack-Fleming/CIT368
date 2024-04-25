package com.fleming.weatherApp.log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class LogFormatter extends Formatter
{
    // ANSI escape code
    public static final String
            ANSI_RESET = "\u001B[0m",       ANSI_BLACK = "\u001B[30m",
            ANSI_RED = "\u001B[31m",        ANSI_GREEN = "\u001B[32m",
            ANSI_YELLOW = "\u001B[33m",     ANSI_BLUE = "\u001B[34m",
            ANSI_PURPLE = "\u001B[35m",     ANSI_CYAN = "\u001B[36m",
            ANSI_WHITE = "\u001B[37m";
    @Override
    public String format(LogRecord record)
    {
        StringBuilder builder = new StringBuilder();

        if (record.getLevel().getName().equals("INFO"))
            builder.append(ANSI_GREEN);
        else if (record.getLevel().getName().equals("WARNING"))
            builder.append(ANSI_YELLOW);
        else if (record.getLevel().getName().equals("SEVERE"))
            builder.append(ANSI_RED);
        else
            builder.append(ANSI_WHITE);

        builder.append("[")
                .append(calcDate(record.getMillis()))
                .append("]")

                .append(" [")
                .append(record.getSourceClassName())
                .append("]")

                .append(" [")
                .append(record.getLevel().getName())
                .append("]")

                .append(ANSI_WHITE)
                .append(" - ")
                .append(record.getMessage());

        Object[] params = record.getParameters();

        if (params != null)
        {
            builder.append("\n\t");
            for (int i = 0; i < params.length; i++)
            {
                builder.append(params[i]);
                if (i < params.length - 1) builder.append(", ");
            }
        }

        builder.append(ANSI_RESET).append("\n");
        return builder.toString();
    }

    private String calcDate(long millis)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date resultDate = new Date(millis);
        return sdf.format(resultDate);
    }
}