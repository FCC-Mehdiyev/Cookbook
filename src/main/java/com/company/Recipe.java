package com.company;

import java.io.Serializable;

public class Recipe extends Food implements Serializable, Comparable<Recipe>{

    // Instance variables
    private double totalTime; // The total time it takes to prepare and make this dish
    private int servings; // The amount of servings this recipe makes

    /** Construct a default Recipe object */
    public Recipe() {
        super();
        this.totalTime = 0;
        this.servings = 0;
    }

    /** Construct a Recipe object with the name and difficulty rating */
    public Recipe(String name, String difficultyRating) {
        super(name, difficultyRating);
        this.totalTime = 0;
        this.servings = 0;
    }

    /** Construct a Recipe object with the name, difficulty rating, total time, and servings */
    public Recipe(String name, String difficultyRating, double totalTime, int servings) {
        super(name, difficultyRating);
        this.totalTime = totalTime;
        this.servings = servings;
    }

    /** Set the total time of the recipe */
    public void setTotalTime(double totalTime) {
        this.totalTime = totalTime;
    }

    /** Set the servings of the recipe */
    public void setServings(int servings) {
        this.servings = servings;
    }

    /** Get the total time of the recipe */
    public double getTotalTime() {
        return this.totalTime;
    }

    /** Get the servings of the recipe */
    public int getServings() {
        return this.servings;
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
                this.servings == ((Recipe) object).getServings();
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
        return super.toString() + "\nTotal Time: " + this.totalTime + "\nServings: " + this.servings;
    }

}
