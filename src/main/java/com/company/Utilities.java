package com.company;

import javafx.collections.ObservableList;
import javafx.scene.control.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Optional;

public class Utilities {

    /**
     * Serialize any given object and save it to a file
     *
     * @param fileName a String to denote the name of the file
     * @param object the Object to save to the file
     */
    public static void serializeObject(String fileName, Object object) {
        // Catch and handle any exceptions
        try {
            // Create the out streams from the file and object
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            // Write the player object to the file
            objectOutputStream.writeObject(object);

            // Close the output streams
            fileOutputStream.close();
            objectOutputStream.close();

            // Print a success message
            System.out.println("The Recipe object has been saved to " + fileName);

        } catch (FileNotFoundException ex) {
            System.out.println("File " + fileName + " not found!");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    /**
     * Deserialize any given file and return its object
     *
     * @param fileName a String to denote the name of the file
     * @return the object in the file
     */
    public static Object deserializeObject(String fileName) {
        // Catch and handle any exceptions
        try {
            // Create the input streams from the file and object
            FileInputStream fileInputStream = new FileInputStream(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            // Extract the object from the file
            Object object = objectInputStream.readObject();

            // Close the input streams
            fileInputStream.close();
            objectInputStream.close();

            // Return the object
            return object;

        } catch (IOException ex) {
            System.out.println("Object not found!");
        } catch (ClassNotFoundException ex) {
            System.out.println("Class not found!");
        }

        // Return null if the code reaches this point
        return null;
    }


    /**
     * Add all recipes from any given list of recipes to the RecipeTableView
     *
     * @param recipeTableData the TableView's data
     * @param recipes the ArrayList of type Recipe to feed into the table
     */
    public static void loadRecipes(ObservableList<RecipeTableProperties> recipeTableData, ArrayList<Recipe> recipes) {

        // Edge cases: Make sure the list exists and has elements within it
        if (recipes == null || recipes.size() == 0) {
            return;
        }

        // Clear the table's current data
        recipeTableData.clear();

        // Sort the list of recipes
        QuickSort<Recipe> recipeQuickSorter = new QuickSort<Recipe>();
        recipeQuickSorter.sort(recipes);

        // Add each recipe to the table
        for (Recipe recipe : recipes) {
            recipeTableData.add(new RecipeTableProperties(recipe.getName(), recipe.getDifficultyRating()));
        }
    }


    /**
     * Create a popup for the user to enter the name of the cookbook they wish to load
     *
     * @param recipeTableData the TableView's data
     * @param recipes the ArrayList of type Recipe to feed into the table
     * @param titleLabel the Label node to retrieve the title of the cookbook from
     * @param initialCheck a boolean value to determine when and where this event is taking place
     */
    public static void loadCookbook(ObservableList<RecipeTableProperties> recipeTableData, ArrayList<Recipe> recipes,
                                    Label titleLabel, boolean initialCheck) {
        // Create a new text popup with an input box
        TextInputDialog cookbookName = new TextInputDialog("<Cookbook Name>");
        cookbookName.setTitle("Load Cookbook");
        cookbookName.setHeaderText(null);
        cookbookName.setContentText("Please enter the name of the cookbook you wish to load:");

        // Get the response value
        Optional<String> cookbookNameResult = cookbookName.showAndWait();
        // Check if the user responded at all
        if (cookbookNameResult.isPresent()) {

            // Create a file name
            String fileName = "Cookbooks\\" + cookbookNameResult.get() + ".dat";

            // Check if this cookbook's file already exists by trying to deserialize from the specific file
            if (Utilities.deserializeObject(fileName) != null) {

                // Clear the recipes table and recipes list
                recipeTableData.clear();
                recipes.clear();

                // Set the new cookbook's title
                titleLabel.setText(cookbookNameResult.get());

                // Extract the recipe list from the file and set it equal to the current recipe list
                recipes = (ArrayList<Recipe>) Utilities.deserializeObject(fileName);
                Utilities.loadRecipes(recipeTableData, recipes); // Add all recipes from the list to the table
            }
            // If it doesn't exist, then let the user know
            else {
                // Create a popup and display it to the user
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error"); // Set the title
                alert.setHeaderText(cookbookNameResult.get() + " does not exist! "); // Set the header text
                alert.showAndWait(); // Display it

                // Check if the app is in the initial opening stage
                if (initialCheck) {
                    // If so, prompt the user again
                    loadCookbook(recipeTableData, recipes, titleLabel, true);
                }
            }
        } else {
            // Check if the app is in the initial opening stage
            if (initialCheck) {
                // If so, prompt the user again
                initialUserPrompt(recipeTableData, recipes, titleLabel);
            }
        }
    }


    /**
     * Create a popup for the user to enter the name of the cookbook they wish to create
     *
     * @param recipeTableData the TableView's data
     * @param recipes the ArrayList of type Recipe to feed into the table
     * @param titleLabel the Label node to set the new cookbook's title
     * @param initialCheck a boolean value to determine when and where this event is taking place
     */
    public static void newCookbook(ObservableList<RecipeTableProperties> recipeTableData, ArrayList<Recipe> recipes,
                                   Label titleLabel, boolean initialCheck) {
        // Create a new text popup with an input box
        TextInputDialog cookbookName = new TextInputDialog("Desserts");
        cookbookName.setTitle("New Cookbook");
        cookbookName.setHeaderText(null);
        cookbookName.setContentText("Please enter your new cookbook's name:");

        // Get the response value
        Optional<String> cookbookNameResult = cookbookName.showAndWait();
        // Check if the user responded at all
        if (cookbookNameResult.isPresent()) {

            // Create a file name
            String fileName = "Cookbooks\\" + cookbookNameResult.get() + ".dat";

            // Check if this cookbook's file already exists by trying to deserialize from the specific file
            if (Utilities.deserializeObject(fileName) == null) {

                // Clear the recipes table and recipes list
                recipeTableData.clear();
                recipes.clear();

                // Set the new cookbook's title
                titleLabel.setText(cookbookNameResult.get());
            }
            // If it already exists, let the user know
            else {
                // Create a popup and display it to the user
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error"); // Set the title
                alert.setHeaderText(cookbookNameResult.get() + " already exists! "); // Set the header text
                alert.showAndWait(); // Display it

                // Check if the app is in the initial opening stage
                if (initialCheck) {
                    // If so, prompt the user again
                    loadCookbook(recipeTableData, recipes, titleLabel, true);
                }
            }
        } else {
            // Check if the app is in the initial opening stage
            if (initialCheck) {
                // If so, prompt the user again
                initialUserPrompt(recipeTableData, recipes, titleLabel);
            }
        }
    }


    /**
     * Create a popup for the user to chose whether to create a new cookbook or to load a preexisting one in
     *
     * @param recipeTableData the TableView's data
     * @param recipes the ArrayList of type Recipe to feed into the table
     * @param titleLabel the Label node to set the new cookbook's title
     */
    public static void initialUserPrompt(ObservableList<RecipeTableProperties> recipeTableData,
                                         ArrayList<Recipe> recipes, Label titleLabel) {
        // Create an alert
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Load/New Cookbook"); // Set the title
        alert.setHeaderText(null); // Disable header text
        alert.setContentText("Either load a preexisting cookbook or create a new one:"); // Set the prompt

        // Create the different options for buttons
        ButtonType loadCookbookButton = new ButtonType("Load Cookbook");
        ButtonType newCookbookButton = new ButtonType("New Cookbook");
        ButtonType closeButton = new ButtonType("Close");

        // Add the buttons to the popup
        alert.getButtonTypes().setAll(loadCookbookButton, newCookbookButton, closeButton);

        // Get the response value
        Optional<ButtonType> response = alert.showAndWait();
        // Check if the user responded at all
        if (response.isPresent()) {
            // User clicked the load button
            if (response.get() == loadCookbookButton) {
                loadCookbook(recipeTableData, recipes, titleLabel, true);
            }
            // User clicked the new button
            else if (response.get() == newCookbookButton){
                newCookbook(recipeTableData, recipes, titleLabel, true);
            }
            // User clicked the close button
            else {
                System.exit(-1);
            }
        }
    }

}
