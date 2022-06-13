// **********************************************************************************
// Class Name: Recipe
// Author: Ayhan Mehdiyev
// File: Cookbook/src/main/java/com/company/Recipe.java
// Description:
//              This is the child of the Food class; this adds ingredients and directions
//              alongside further details about the dish.
// **********************************************************************************
package com.company;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Recipe extends Food implements Serializable, Comparable<Recipe>{

    // Instance variables
    private double totalTime; // The total time it takes to prepare and make this dish
    private int servings; // The amount of servings this recipe makes
    private HashMap<String, IngredientUnits> ingredientsList; // The list of ingredients needed to make the dish
    private Queue<String> directionsList; // The list of directions on how to make this dish


    /** Construct a default Recipe object */
    public Recipe() {
        super();
        this.totalTime = 0;
        this.servings = 0;
    }

    /** Construct a copy object */
    public Recipe(Recipe recipe) {
        super(recipe.getName(), recipe.getDifficultyRating());
        this.totalTime = recipe.totalTime;
        this.servings = recipe.servings;
        this.ingredientsList = recipe.getIngredientsList();
        this.directionsList = recipe.getDirectionsList();
    }

    /** Construct a Recipe object with the name and difficulty rating */
    public Recipe(String name, String difficultyRating) {
        super(name, difficultyRating);
        this.totalTime = 0;
        this.servings = 0;
        this.ingredientsList = new HashMap<String, IngredientUnits>();
        this.directionsList = new LinkedList<String>();
    }

    /** Construct a Recipe object with the name, difficulty rating, total time, and servings */
    public Recipe(String name, String difficultyRating, double totalTime, int servings) {
        super(name, difficultyRating);
        this.totalTime = totalTime;
        this.servings = servings;
        this.ingredientsList = new HashMap<String, IngredientUnits>();
        this.directionsList = new LinkedList<String>();
    }

    /** Construct a Recipe object with the name, difficulty rating, total time, servings, ingredients list, and directions list */
    public Recipe(String name, String difficultyRating, double totalTime, int servings, HashMap<String, IngredientUnits> ingredientsList, Queue<String> directionsList) {
        super(name, difficultyRating);
        this.totalTime = totalTime;
        this.servings = servings;
        this.ingredientsList = ingredientsList;
        this.directionsList = directionsList;
    }

    /** Set the total time of the recipe */
    public void setTotalTime(double totalTime) {
        this.totalTime = totalTime;
    }

    /** Set the servings of the recipe */
    public void setServings(int servings) {
        this.servings = servings;
    }

    /** Set the ingredients list of the recipe */
    public void setIngredientsList(HashMap<String, IngredientUnits> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    /** Set the directions list of the recipe */
    public void setDirectionsList(Queue<String> directionsList) {
        this.directionsList = directionsList;
    }

    /** Get the total time of the recipe */
    public double getTotalTime() {
        return this.totalTime;
    }

    /** Get the servings of the recipe */
    public int getServings() {
        return this.servings;
    }

    /** Get the ingredients list of the recipe */
    public HashMap<String, IngredientUnits> getIngredientsList() {
        return this.ingredientsList;
    }

    /** Get the directions list of the recipe */
    public Queue<String> getDirectionsList() {
        return this.directionsList;
    }

    /**
     * Check if any given object is equal to this object
     *
     * @param object the object to compare with
     * @return a boolean value denoting if both objects are equal or not
     */
    @Override
    public boolean equals(Object object) {
        return super.equals(object) && this.totalTime == ((Recipe) object).getTotalTime() &&
                this.servings == ((Recipe) object).getServings() &&
                this.ingredientsList.equals(((Recipe) object).getIngredientsList()) &&
                this.directionsList.equals(((Recipe) object).getDirectionsList());
    }

    /**
     * Compare any given recipe's name to this object's to check which is alphabetically higher
     *
     * @param recipe the Recipe object to compare this object with
     * @return an integer value depending on which comes first
     */
    @Override
    public int compareTo(Recipe recipe) {
        return super.getName().compareTo(recipe.getName());
    }

    /**
     * Convert this object to its most accurate representation as a String
     *
     * @return a String version of this object
     */
    @Override
    public String toString() {
        return super.toString() + "\nTotal Time: " + this.totalTime + "\nServings: " + this.servings +
                "\nIngredients List: " + this.ingredientsList.toString() + "\nDirections List: " +
                this.directionsList.toString();
    }

}
