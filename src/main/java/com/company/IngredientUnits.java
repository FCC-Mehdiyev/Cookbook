// **********************************************************************************
// Class Name: IngredientUnits
// Author: Ayhan Mehdiyev
// File: Cookbook/src/main/java/com/company/IngredientUnits.java
// Description:
//              This class enables the combination of the amount of an ingredient needed
//              to be paired with the measuring units of that ingredient.
// **********************************************************************************
package com.company;

import java.io.Serializable;

public class  IngredientUnits implements Serializable, Comparable<IngredientUnits> {

    // Instance variables
    private double amount; // The amount of the ingredient needed in the recipe
    private Enum<MeasuringUnits> units; // The measuring units for the amount

    /** Construct a default object */
    public IngredientUnits() {
        this.amount = 0;
        this.units = null;
    }

    /** Construct a copy object */
    public IngredientUnits(IngredientUnits ingredientUnit) {
        this.amount = ingredientUnit.getAmount();
        this.units = ingredientUnit.getUnits();
    }

    /** Construct an object with the amount and units */
    public IngredientUnits(double amount, Enum<MeasuringUnits> units) {
        this.amount = amount;
        this.units = units;
    }

    /** Set the amount */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /** Set the units */
    public void setUnits(Enum<MeasuringUnits> units) {
        this.units = units;
    }

    /** Get the amount */
    public double getAmount() {
        return this.amount;
    }

    /** Get the units */
    public Enum<MeasuringUnits> getUnits() {
        return this.units;
    }

    /**
     * Check if any given object is equal to this object
     *
     * @param object the object to compare with
     * @return a boolean value denoting if both objects are equal or not
     */
    @Override
    public boolean equals(Object object) {
        return this.amount == ((IngredientUnits) object).getAmount() && this.units == ((IngredientUnits) object).getUnits();
    }

    /**
     * Compare any given recipe's name to this object's to check which is alphabetically higher
     *
     * @param ingredientUnits the IngredientUnits object to compare this object with
     * @return an integer value depending on which comes first
     */
    @Override
    public int compareTo(IngredientUnits ingredientUnits) {
        return Double.compare(this.convertToGrams(new IngredientUnits(this.amount, this.units)), this.convertToGrams(ingredientUnits));
    }


    /** A helper method to convert any unit to grams */
    private double convertToGrams(IngredientUnits ingredientUnits) {
        switch (ingredientUnits.getUnits().toString()) {
            case "TEASPOONS":
                return ingredientUnits.getAmount() * 5.69; // 1 teaspoon = 5.69 grams
            case "TABLESPOONS":
                return ingredientUnits.getAmount() * 14.3; // 1 tablespoon = 14.3 grams
            case "FLUID_OUNCES":
                return ingredientUnits.getAmount() * 28.35; // 1 fluid ounce = 28.35 grams
            case "GILLS":
                return ingredientUnits.getAmount() * 118.294; // 1 gill = 118.294 grams
            case "CUPS":
                return ingredientUnits.getAmount() * 128; // 1 cup = 128 grams
            case "PINTS":
                return ingredientUnits.getAmount() * 403.2; // 1 pint = 403.2 grams
            case "QUARTS":
                return ingredientUnits.getAmount() * 806.4; // 1 quart = 806.4 grams
            case "GALLONS":
                return ingredientUnits.getAmount() * 3785.41; // 1 gallon = 3785.41 grams
            case "MILLILITERS":
            case "GRAMS":
                return ingredientUnits.getAmount(); // 1 milliliter = 1 gram && 1 gram = 1 gram
            case "LITERS":
            case "KILOGRAMS":
                return ingredientUnits.getAmount() * 1000; // 1 liter = 1000 grams && 1 kilogram = 1000 grams
            case "DECILITERS":
                return ingredientUnits.getAmount() * 100; // 1 deciliter = 100 grams
            case "POUNDS":
                return ingredientUnits.getAmount() * 453.952; // 1 pound = 453.952 grams
            case "OUNCES":
                return ingredientUnits.getAmount() * 28.3495; // 1 ounce = 28.3495 grams
            case "MILLIGRAMS":
                return ingredientUnits.getAmount() * 0.001; // 1 milligram = 0.001 grams
            default:
                return 0;
        }
    }

    /**
     * Convert this object to its most accurate representation as a String
     *
     * @return a String version of this object
     */
    @Override
    public String toString() {
        return "Ingredient Amount: " + this.amount + "\nMeasuring Units: " + this.units.toString();
    }


    /** Inner class to determine all possible options for measuring units */
    public enum MeasuringUnits {
        TEASPOONS,
        TABLESPOONS,
        FLUID_OUNCES,
        GILLS,
        CUPS,
        PINTS,
        QUARTS,
        GALLONS,
        MILLILITERS,
        LITERS,
        DECILITERS,
        POUNDS,
        OUNCES,
        MILLIGRAMS,
        GRAMS,
        KILOGRAMS;
    }



}
