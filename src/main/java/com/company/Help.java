// **********************************************************************************
// Class Name: Help
// Author: Ayhan Mehdiyev
// File: Cookbook/src/main/java/com/company/Help.java
// Description:
//              This class provides a singular static method which returns information
//              about the application.
// **********************************************************************************
package com.company;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Help {

    /** Private constructor to prevent object creation because this class only provides a static only helper method */
    private Help() {};

    /** Returns a String about what the application is and what it does */
    public static String about() {
        return "Cookbook is an application where you can store and keep track of as many recipes as you want!\n" +
                "You cannot create duplicate cookbooks, and are not intended to create recipes of the same name.\n" +
                "However, you can get around the recipes restriction through editing the recipe to change the name.";
    }

    /** Return a String which lists each cookbook that is currently in the saved folder */
    public static String getCookbooks() {
        // Create a list to store each cookbook name
        List<String> cookbookNames = new ArrayList<String>();
        // Get into the directory and retrieve a list of all files in it
        File[] files = new File("Cookbooks").listFiles();
        assert files != null; // The folder should exist, so assert it
        // Traverse through the list of files
        for (File file : files) {
            // Check if it truly is a file
            if (file.isFile())
                // Add the file name (cutting off the type via a substring) to the list of names
                cookbookNames.add(file.getName().substring(0, file.getName().length() - 4));
        }

        // Return the names
        return "Cookbooks: " + cookbookNames;
    }

}