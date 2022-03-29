package com.company;

public class Help {

    private static String description; // Description for about()

    /** Construct a default Help object */
    public Help() {
        description = "Cookbook is an application where you can store and keep track of as many recipes as you want!";
    }

    /** Construct a Help object with the description */
    public Help(String description) {
        Help.description = description;
    }

    /** Get the description */
    public static String about() {
        return (description != null)? description: "Cookbook is an application where you can store and keep" +
                " track of as many recipes as you want!";
    }

}