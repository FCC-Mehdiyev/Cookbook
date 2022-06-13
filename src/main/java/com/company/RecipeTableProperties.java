// **********************************************************************************
// Class Name: RecipeTableProperties
// Author: Ayhan Mehdiyev
// File: Cookbook/src/main/java/com/company/RecipeTableProperties.java
// Description:
//              This class provides data for the columns of the TableView node
//              meant to hold recipes, which can be found in the first screen.
// **********************************************************************************
package com.company;

import javafx.scene.control.Button;

public class RecipeTableProperties {

    // Instance variables
    private String recipeName; // The name of the recipe
    private String difficultyRating; // A rating from 1 to 3 on how difficult it is to make this dish
    private String totalTime; // The total time it will take to create this dish
    private String servings; // The amount of servings this recipe will create
    private final Button openButton; // The button to open the recipe, located on the third column
    private final Button deleteButton; // The button to delete the row, located on the fourth column

    /** Construct a default Properties object */
    public RecipeTableProperties() {
        this.recipeName = "";
        this.difficultyRating = "";
        this.totalTime = "";
        this.servings = "";
        this.openButton = new Button("|>");
        this.deleteButton = new Button("X");
    }

    /** Construct a copy object */
    public RecipeTableProperties(RecipeTableProperties recipeTableProperties) {
        this.recipeName = recipeTableProperties.getRecipeName();
        this.difficultyRating = recipeTableProperties.getDifficultyRating();
        this.totalTime = recipeTableProperties.getTotalTime();
        this.servings = recipeTableProperties.getServings();
        this.openButton = recipeTableProperties.getOpenButton();
        this.deleteButton = recipeTableProperties.getDeleteButton();
    }

    /** Construct a Properties object with the recipe name, and difficulty rating */
    public RecipeTableProperties(String recipeName, String difficultyRating, String totalTime, String servings) {
        this.recipeName = recipeName;
        this.difficultyRating = difficultyRating;
        this.totalTime = totalTime;
        this.servings = servings;
        this.openButton = new Button("|>");
        this.deleteButton = new Button("X");
    }

    /** Set the recipe name */
    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    /** Set the difficulty rating */
    public void setDifficultyRating(String difficultyRating) {
        this.difficultyRating = difficultyRating;
    }

    /** Set the total time */
    public void setTotalTime(String totalTime) { this.totalTime = totalTime; }

    /** Set the servings */
    public void setServings(String servings) { this.servings = servings; }

    /** Get the recipe name */
    public String getRecipeName() {
        return this.recipeName;
    }

    /** Get the difficulty rating */
    public String getDifficultyRating() {
        return this.difficultyRating;
    }

    /** Get the total time */
    public String getTotalTime() { return this.totalTime; }

    /** Get the servings */
    public String getServings() { return this.servings; }

    /** Get the open button */
    public Button getOpenButton() { return this.openButton; }

    /** Get the delete button */
    public Button getDeleteButton() { return this.deleteButton; }

    /**
     * Convert this object to its most accurate representation as a String
     *
     * @return a String version of this object
     */
    @Override
    public String toString() {
        return "Recipe Name: " + this.recipeName + "\nDifficulty Rating: " + this.difficultyRating +
                "\nTotal Time: " + this.totalTime + "\nServings: " + this.servings;
    }


}
