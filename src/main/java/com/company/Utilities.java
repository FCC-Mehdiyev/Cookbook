// **********************************************************************************
// Class Name: Utilities
// Author: Ayhan Mehdiyev
// File: Cookbook/src/main/java/com/company/Utilities.java
// Description:
//              This class provides several static helper methods to help run the
//              background processes, like loading a screen or saving an object to a file.
// **********************************************************************************
package com.company;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class Utilities {

    /** Private constructor to prevent object creation because this class only provides static helper methods */
    private Utilities() {};

    /**
     * Serialize any given object and save it to a file
     *
     * @param fileName a String to denote the name of the file
     * @param object the Object to save to the file
     */
    public static void serializeObject(String fileName, Object object) {
        // Catch any I/O exceptions
        try {
            // Create the output streams from the file and object
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            // Write the player object to the file
            objectOutputStream.writeObject(object);

            // Close the output streams
            fileOutputStream.close();
            objectOutputStream.close();

            System.out.println("The Recipe object has been saved to " + fileName);
        }
        catch (FileNotFoundException ex) {
            System.out.println("File " + fileName + " not found!");
        }
        catch (IOException ex) {
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
        // Catch any I/O exceptions
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

        }
        catch (IOException ex) {
            System.out.println("Object not found!");
        }
        catch (ClassNotFoundException ex) {
            System.out.println("Class not found!");
        }

        // This will never be reached
        return null;
    }


    /**
     * Create a popup for the user to enter the name of the cookbook they wish to load
     *
     * @param recipeTableData the TableView's data
     * @param recipes the ArrayList of type Recipe to feed into the table
     * @param titleLabel the Label node to retrieve the title of the cookbook from
     * @param initialCheck a boolean value to determine when and where this event is taking place
     */
    public static ArrayList<Recipe> loadCookbook(ObservableList<RecipeTableProperties> recipeTableData, ArrayList<Recipe> recipes, Label titleLabel, boolean initialCheck) {
        // Create a new text popup with an input box
        TextInputDialog cookbookName = new TextInputDialog("Cookbook Name");
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
                Alert alert = Utilities.createAlert(Alert.AlertType.ERROR, "Error", cookbookNameResult.get() + " does not exist!", null);
                alert.showAndWait(); // Display it

                // Check if the app is in the initial opening stage
                if (initialCheck)
                    // If so, prompt the user again
                    loadCookbook(recipeTableData, recipes, titleLabel, true);
            }
        }
        else {
            // Check if the app is in the initial opening stage
            if (initialCheck)
                // If so, prompt the user again
                initialUserPrompt(recipeTableData, recipes, titleLabel);
        }

        return recipes;
    }


    /**
     * Create a popup for the user to enter the name of the cookbook they wish to create
     *
     * @param recipeTableData the TableView's data
     * @param recipes the ArrayList of type Recipe to feed into the table
     * @param titleLabel the Label node to set the new cookbook's title
     * @param initialCheck a boolean value to determine when and where this event is taking place
     */
    public static void newCookbook(ObservableList<RecipeTableProperties> recipeTableData, ArrayList<Recipe> recipes, Label titleLabel, boolean initialCheck) {
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
                Alert alert = Utilities.createAlert(Alert.AlertType.ERROR, "Error", cookbookNameResult.get() + " already exists!", null);
                alert.showAndWait(); // Display it

                // Check if the app is in the initial opening stage
                if (initialCheck)
                    // If so, prompt the user again
                    newCookbook(recipeTableData, recipes, titleLabel, true);
            }
        }
        else {
            // Check if the app is in the initial opening stage
            if (initialCheck)
                // If so, prompt the user again
                initialUserPrompt(recipeTableData, recipes, titleLabel);
        }
    }


    /**
     * Create a popup for the user to chose whether to create a new cookbook or to load a preexisting one in
     *
     * @param recipeTableData the TableView's data
     * @param recipes the ArrayList of type Recipe to feed into the table
     * @param titleLabel the Label node to set the new cookbook's title
     */
    public static ArrayList<Recipe> initialUserPrompt(ObservableList<RecipeTableProperties> recipeTableData, ArrayList<Recipe> recipes, Label titleLabel) {
        // Create an alert
        Alert alert = Utilities.createAlert(Alert.AlertType.CONFIRMATION, "Load/New Cookbook", null, "Either load a preexisting cookbook or create a new one:");

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
            if (response.get() == loadCookbookButton)
                recipes = loadCookbook(recipeTableData, recipes, titleLabel, true);
            // User clicked the new button
            else if (response.get() == newCookbookButton)
                newCookbook(recipeTableData, recipes, titleLabel, true);
            // User clicked the close button
            else
                System.exit(-1);
        }

        return recipes;
    }

    /**
     * Given any ArrayList of type Recipe, find any given recipe within it
     *
     * @param recipes an ArrayList of type Recipe
     * @param recipe the recipe to look for within the ArrayList
     * @return the index of the recipe in an ArrayList of type Recipe
     */
    public static int findRecipe(ArrayList<Recipe> recipes, Recipe recipe) {
        // Iterate through all the recipes in the given ArrayList
        for (Recipe currRecipe : recipes)
            // Check if the name, difficulty rating, total time, and servings are equal to one another
            if (currRecipe.getName().equals(recipe.getName()) && currRecipe.getDifficultyRating().equals(recipe.getDifficultyRating()) &&
                    currRecipe.getTotalTime() == recipe.getTotalTime() && currRecipe.getServings() == recipe.getServings())
                // Return the index of the recipe
                return recipes.indexOf(currRecipe);

        // If the recipe cannot be found, return a -1 to signify the list lacking the recipe
        return -1;
    }

    /**
     * Add all recipes from any given list of recipes to the RecipeTableView
     *
     * @param recipeTableData the TableView's data
     * @param recipes the ArrayList of type Recipe to feed into the table
     */
    public static void loadRecipes(ObservableList<RecipeTableProperties> recipeTableData, ArrayList<Recipe> recipes) {

        // Edge cases: Make sure the list exists and has elements within it
        if (recipes == null || recipes.size() == 0)
            return;

        // Clear the table's current data
        recipeTableData.clear();

        // Sort the list of recipes
        QuickSort<Recipe> recipeQuickSorter = new QuickSort<Recipe>();
        recipeQuickSorter.sort(recipes);

        // Add each recipe to the table
        for (Recipe recipe : recipes)
            recipeTableData.add(new RecipeTableProperties(recipe.getName(), recipe.getDifficultyRating(), String.valueOf(recipe.getTotalTime()), String.valueOf(recipe.getServings())));
    }

    /**
     * Add all ingredients from any given recipe to the IngredientsTableView
     *
     * @param ingredientsTableData the TableView's data
     * @param recipe the Recipe to retrieve the ingredients from and feed it to the table's data
     */
    public static void loadIngredients(ObservableList<IngredientsTableProperties> ingredientsTableData, Recipe recipe) {

        // Edge cases: Make sure the list exists and has elements within it
        if (recipe.getIngredientsList() == null || recipe.getIngredientsList().size() == 0)
            return;

        // Clear the table's current data
        ingredientsTableData.clear();

        // Add each ingredient to the table
        for (HashMap.Entry<String, IngredientUnits> ingredientEntry : recipe.getIngredientsList().entrySet())
            ingredientsTableData.add(new IngredientsTableProperties(ingredientEntry.getKey(), String.valueOf(ingredientEntry.getValue().getAmount()), ingredientEntry.getValue().getUnits().toString()));
    }

    /**
     * Add all directions from any given recipe to the DirectionsTableView
     *
     * @param directionsTableData the TableView's data
     * @param recipe the Recipe to retrieve the directions from and feed it to the table's data
     */
    public static void loadDirections(ObservableList<DirectionsTableProperties> directionsTableData, Recipe recipe) {

        // Edge cases: Make sure the list exists and has elements within it
        if (recipe.getDirectionsList() == null || recipe.getDirectionsList().size() == 0)
            return;

        // Clear the table's current data
        directionsTableData.clear();

        // Add each direction to the table
        for (String direction : recipe.getDirectionsList())
            directionsTableData.add(new DirectionsTableProperties(direction));
    }


    /**
     * Helper method to create a button
     *
     * @param text the text that will be placed within the button
     * @param weight the text weight (i.e, bold, normal, etc.)
     * @param fontSize the size of the text
     * @param backgroundColor the button color in hex
     * @param minWidth the minimum width of the button
     * @param minHeight the minimum height of the button
     * @param layoutX the x coordinate within the plane
     * @param layoutY the y coordinate within the plane
     * @return a newly created Button based off the inputs from the user
     */
    public static Button createButton(String text, FontWeight weight, int fontSize, String backgroundColor, int minWidth, int minHeight, int layoutX, int layoutY) {
        // Create a button from the arguments
        Button button = new Button(text);
        button.setMinSize(minWidth, minHeight);
        button.setLayoutX(layoutX);
        button.setLayoutY(layoutY);
        button.setStyle("-fx-border-color: white");
        button.setTextFill(Color.WHITE);
        button.setFont(Font.font("Verdana", weight, fontSize));
        button.setBackground(new Background(new BackgroundFill(
                Color.valueOf(backgroundColor),
                CornerRadii.EMPTY,
                Insets.EMPTY
        )));
        button.setOnMouseEntered(e -> button.setStyle("-fx-border-color: red"));
        button.setOnMouseExited(e -> button.setStyle("-fx-border-color: white"));
        return button;
    }

    /**
     * Helper method to create a TextInputDialog
     *
     * @param ghostText text that will be initially placed in the input box
     * @param dialogTitle the title of the popup
     * @param promptText the content text
     * @return a newly created TextInputDialog based off the inputs from the user
     */
    public static TextInputDialog createTextInputDialog(String ghostText, String dialogTitle, String promptText) {
        // Create a TextInputDialog from the arguments
        TextInputDialog textInputDialog = new TextInputDialog(ghostText);
        textInputDialog.setTitle(dialogTitle);
        textInputDialog.setHeaderText(null);
        textInputDialog.setContentText(promptText);
        return textInputDialog;
    }

    /**
     * Helper method to create a ChoiceDialog
     *
     * @param choices a list with the different choices that the user can pick from
     * @param ghostText text that will be initially placed in the choice box
     * @param dialogTitle the title of the popup
     * @param promptText the content text
     * @return a newly created ChoiceDialog based off the inputs from the user
     */
    public static ChoiceDialog<String> createChoiceDialog(List<String> choices, String ghostText, String dialogTitle, String promptText) {
        // Create a ChoiceDialog from the arguments
        ChoiceDialog<String> choiceDialog = new ChoiceDialog<>(ghostText, choices);
        choiceDialog.setTitle(dialogTitle);
        choiceDialog.setHeaderText(null);
        choiceDialog.setContentText(promptText);
        return choiceDialog;
    }

    /**
     * Helper method to create a TableColumn
     *
     * @param columnHeader the header text that will distinguish what the column holds
     * @param minWidth the minimum width of the column
     * @param maxWidth the maximum width of the column
     * @param cellValueProperty the ValueProperty linked from the TableProperties class
     * @return a newly created TableColumn based off the inputs from the user
     */
    public static TableColumn<?, ?> createTableColumn(String columnHeader, int minWidth, int maxWidth, String cellValueProperty) {
        // Create a table column from the arguments
        TableColumn<?, ?> tableColumn = new TableColumn<>(columnHeader);
        tableColumn.setMinWidth(minWidth);
        tableColumn.setMaxWidth(maxWidth);
        tableColumn.setCellValueFactory(new PropertyValueFactory<>(cellValueProperty));
        return tableColumn;
    }

    /**
     * Helper method to create a TableView
     *
     * @param layoutX the x coordinate of the TableView
     * @param layoutY the y coordinate of the TableView
     * @return a newly created TableView based off the inputs from the user
     */
    public static TableView<?> createTableView(int layoutX, int layoutY) {
        // Create a table view from the arguments
        TableView<?> tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setPadding(new Insets(10, 10, 10, 10));
        tableView.setFocusTraversable(false);
        tableView.setLayoutX(layoutX);
        tableView.setLayoutY(layoutY);
        return tableView;
    }

    /**
     * Helper method to create a Label
     *
     * @param text the text that the label will contain
     * @param layoutX the x coordinate within the plane
     * @param layoutY the y coordinate within the plane
     * @return a newly created Label based off the inputs from the user
     */
    public static Label createLabel(String text, int fontSize, int layoutX, int layoutY) {
        // Create a label from the arguments
        Label label = new Label(text);
        label.setFont(Font.font("Consolas", FontWeight.BOLD, fontSize));
        label.setLayoutX(layoutX);
        label.setLayoutY(layoutY);
        label.setTextFill(Color.WHITE);
        return label;
    }

    /**
     * Helper method to create an Alert
     *
     * @param alertType the type of alert, i.e: confirmation, warning, error, etc.
     * @param title the title of the alert
     * @param headerText the header text of the alert
     * @param contentText the content text of the alert
     * @return a newly created Alert based off the inputs from the user
     */
    public static Alert createAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        // Create an alert from the arguments
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.setResizable(false);
        return alert;
    }

    /**
     * Returns a String which lists each cookbook that is currently in the saved folder
     *
     * @return a String that contains every saved cookbook
     */
    public static String getCookbooks() {
        // Create a list to store each cookbook name
        List<String> cookbookNames = new ArrayList<String>();
        // Get into the directory and retrieve a list of all files in it
        File[] files = new File("Cookbooks").listFiles();
        assert files != null; // The folder should exist, so assert it
        // Traverse through the list of files
        for (File file : files) {
            // Check if it truly is a file
            if (file.isFile())
                // Add the file name (cutting off the type via a substring) to the list of names
                cookbookNames.add(file.getName().substring(0, file.getName().length() - 4));
        }

        // Return the names
        return "Cookbooks: " + cookbookNames;
    }

    /**
     * Returns a String about what the application is and what it does
     *
     * @return a String containing information about the application
     */
    public static String about() {
        return "Cookbook is an application where you can store and keep track of as many recipes as you want!\n" +
                "You cannot create duplicate cookbooks, and are not intended to create recipes of the same name.\n" +
                "However, you can get around the recipes restriction through editing the recipe to change the name.";
    }
}
