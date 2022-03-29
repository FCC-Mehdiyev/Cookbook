package com.company;

import java.io.Serializable;

public abstract class Food implements Serializable {

    // Instance variables
    private String name; // Name of the food
    private String difficultyRating; // Difficulty of making the food (represented in stars from 1 to 3)

    /** Construct a default Food object */
    public Food() {
        this.name = "";
        this.difficultyRating = "";
    }

    /** Construct a Food object with the name and difficulty rating */
    public Food(String name, String difficultyRating) {
        this.name = name;
        this.difficultyRating = difficultyRating;
    }

    /** Set the name of the food */
    public void setName(String name) {
        this.name = name;
    }

    /** Set the difficulty rating of making the food */
    public void setDifficultyRating(String difficultyRating) {
        this.difficultyRating = difficultyRating;
    }

    /** Get the name of the food */
    public String getName() {
        return this.name;
    }

    /** Get the difficulty rating of making the food */
    public String getDifficultyRating() {
        return this.difficultyRating;
    }

    /**
     * Check if any given object is equal to this object
     *
     * @param object the object to compare with
     * @return a boolean value denoting if both objects are equal or not
     */
    @Override
    public boolean equals(Object object) {
        return this.name.equals(((Food) object).getName()) &&
                this.difficultyRating.equals(((Food) object).getDifficultyRating());
    }

    /**
     * Convert this object to its most accurate representation as a String
     *
     * @return a String version of this object
     */
    @Override
    public String toString() {
        return "Food Name: " + this.name + "\nFood Difficulty Rating: " + this.difficultyRating;
    }


}
