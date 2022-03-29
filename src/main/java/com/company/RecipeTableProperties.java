package com.company;

import javafx.scene.control.Button;

public class RecipeTableProperties {

    // Instance variables
    private String recipeName; // The name of the recipe
    private String difficultyRating; // A rating from 1 to 3 on how difficult it is to make this food
    private Button openButton; // The button to open the recipe, located on the third column
    private Button deleteButton; // The button to delete the row, located on the fourth column

    /** Construct a default Properties object */
    public RecipeTableProperties() {
        this.recipeName = "";
        this.difficultyRating = "";
        this.openButton = new Button("|>");
        this.deleteButton = new Button("X");
    }

    /** Construct a Properties object with the recipe name, and difficulty rating */
    public RecipeTableProperties(String recipeName, String difficultyRating) {
        this.recipeName = recipeName;
        this.difficultyRating = difficultyRating;
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

    /** Get the recipe name */
    public String getRecipeName() {
        return this.recipeName;
    }

    /** Get the difficulty rating */
    public String getDifficultyRating() {
        return this.difficultyRating;
    }

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
        return "Recipe Name: " + this.recipeName + "\nDifficulty Rating: " + this.difficultyRating;
    }


}
