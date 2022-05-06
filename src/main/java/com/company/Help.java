// **********************************************************************************
// Class Name: Help
// Author: Ayhan Mehdiyev
// File: Cookbook/src/main/java/com/company/Help.java
// Description:
//              This class provides a singular static method which returns information
//              about the application.
// **********************************************************************************
package com.company;

public class Help {

    /** Private constructor to prevent object creation because this class only provides a static only helper method */
    private Help() {};

    /** Returns a String about what the application is and what it does */
    public static String about() {
        return "Cookbook is an application where you can store and keep track of as many recipes as you want!\n" +
                "You cannot create duplicate cookbooks, and are not intended to create recipes of the same name.\n" +
                "However, you can get around the recipes restriction through editing the recipe to change the name.";
    }

}