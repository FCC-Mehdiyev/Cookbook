package com.company;

import java.util.ArrayList;

public class RecipeFinder extends Thread{

    // Instance variables
    private ArrayList<Recipe> recipes; // The list of recipes to search from
    private Recipe recipe; // The specific recipe to find
    private int index; // The index of the specific recipe

    /** Construct a new RecipeFinder object with the recipes and specific recipe */
    public RecipeFinder(ArrayList<Recipe> recipes, Recipe recipe) {
        this.recipes = recipes;
        this.recipe = recipe;
        this.index = 0;
    }

    /** Get the index of the specific recipe */
    public int getIndex() {
        return this.index;
    }


    /**
     * Run method for the thread: Find the specific recipe from the given list of recipes
     *
     */
    @Override
    public void run() {
        // Iterate through all the recipes in the given ArrayList
        for (Recipe currRecipe : recipes) {
            // Check if the name, difficulty rating, total time, and servings are equal to one another
            if (currRecipe.getName().equals(recipe.getName()) && currRecipe.getDifficultyRating().equals(recipe.getDifficultyRating()) &&
                    currRecipe.getTotalTime() == recipe.getTotalTime() && currRecipe.getServings() == recipe.getServings()) {
                this.index = recipes.indexOf(currRecipe);
                return;
            }
        }
        this.index = -1;
    }

}
