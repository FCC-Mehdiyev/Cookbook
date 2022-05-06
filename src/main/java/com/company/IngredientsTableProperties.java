// **********************************************************************************
// Class Name: IngredientsTableProperties
// Author: Ayhan Mehdiyev
// File: Cookbook/src/main/java/com/company/IngredientsTableProperties.java
// Description:
//              This class provides data for the columns of the TableView node
//              meant to hold ingredients, which can be found in the second screen.
// **********************************************************************************
package com.company;

import javafx.scene.control.Button;

public class IngredientsTableProperties {

    // Instance variables
    private String ingredientName; // The name of the ingredient
    private String ingredientAmount; // The amount of the ingredient used in the recipe
    private String measuringUnits; // The units of the amount
    private Button deleteButton; // The button to delete the row, located on the fourth column

    /** Construct a default Properties object */
    public IngredientsTableProperties() {
        this.ingredientName = "";
        this.ingredientAmount = "";
        this.measuringUnits = "";
        this.deleteButton = new Button("X");
    }

    /** Construct a copy object */
    public IngredientsTableProperties(IngredientsTableProperties ingredientsTableProperties) {
        this.ingredientName = ingredientsTableProperties.getIngredientName();
        this.ingredientAmount = ingredientsTableProperties.getIngredientAmount();
        this.measuringUnits = ingredientsTableProperties.getMeasuringUnits();
        this.deleteButton = ingredientsTableProperties.getDeleteButton();
    }

    /** Construct a Properties object with the ingredient name, amount, and units */
    public IngredientsTableProperties(String ingredientName, String ingredientAmount, String measuringUnits) {
        this.ingredientName = ingredientName;
        this.ingredientAmount = ingredientAmount;
        this.measuringUnits = measuringUnits;
        this.deleteButton = new Button("X");
    }

    /** Set the ingredient name */
    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    /** Set the ingredient amount */
    public void setIngredientAmount(String ingredientAmount) {
        this.ingredientAmount = ingredientAmount;
    }

    /** Set the units of the amount */
    public void setAmountUnits(String measuringUnits) {
        this.measuringUnits = measuringUnits;
    }

    /** Get the ingredient name */
    public String getIngredientName() {
        return this.ingredientName;
    }

    /** Get the ingredient amount */
    public String getIngredientAmount() {
        return this.ingredientAmount;
    }

    /** Get the amount units */
    public String getMeasuringUnits() {
        return this.measuringUnits;
    }

    /** Get the delete button */
    public Button getDeleteButton() {
        return this.deleteButton;
    }

    /**
     * Convert this object to its most accurate representation as a String
     *
     * @return a String version of this object
     */
    @Override
    public String toString() {
        return "Ingredient Name: " + this.ingredientName + "\nIngredient Amount: " + this.ingredientAmount +
                "\nAmount Units: " + this.measuringUnits;
    }



}
