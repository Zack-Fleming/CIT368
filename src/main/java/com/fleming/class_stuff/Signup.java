package com.fleming.class_stuff;

import com.fleming.Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;

public class Signup
{
    public static void main(String[] args) throws FileNotFoundException
    {
        //prompt user for username
        Scanner in = new Scanner(System.in);
        System.out.print("Enter a username\n>");
        String  user = in.nextLine().trim(),
                pass = "";

        //check if the user is in the whitelist
        if(!inWhiteList(user))
        {
            System.out.println("you are not in the list...");
            System.exit(1);
        }
        else
        {
            System.out.print("Enter password\n>");
            pass = in.nextLine();

        }
    }

    private static boolean inWhiteList(String user) throws FileNotFoundException
    {
        // open file
        File f = new File("src/main/resources/white.list");
        Scanner whitelist = new Scanner(f);
        whitelist.nextLine();

        // loop to check for input
        while (whitelist.hasNext())
        {
            String[] fields = whitelist.nextLine().split(":");
            String id = fields[0], username = fields[1];

            if (username.equals(user)) // if found, exit
            {
                whitelist.close();
                return true;
            }
        }

        // else return false and close file
        whitelist.close();
        return false;
    }
}