// **********************************************************************************
// Title: Cookbook
// Author: Ayhan Mehdiyev
// Course Section: CMIS202-ONL1 (Seidel) Spring 2022
// File: Cookbook\src\main\java\com\company\Main.java
// Description:
//              This is my major project assignment: Cookbook.
//              Essentially, this application will allow users
//              to create various cookbooks and save all kinds
//              of recipes within the books.
// **********************************************************************************
package com.company;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class Main extends Application {

    private ArrayList<Recipe> recipes = new ArrayList<>(); // An ArrayList to hold all current recipes

    @Override
    public void start(Stage primaryStage) {
        // Create the parent pane to hold all other nodes
        Pane pane = new Pane();
        pane.setMinSize(900, 650); // Set the size of the pane

        // Create a title label for the cookbook
        Label titleLabel = new Label();
        titleLabel.setFont(Font.font("Consolas", FontWeight.BOLD, 40)); // Set the font
        titleLabel.setLayoutX(25); // Position it

        /* ---Help Button--- */
        Button helpButton = new Button("?");
        helpButton.setMinSize(25, 25); // Set the size
        // Set the positioning
        helpButton.setLayoutX(850);
        helpButton.setLayoutY(10);
        helpButton.setFocusTraversable(false);
        // Event Handler for help button click
        helpButton.setOnAction(e -> {
            // Create a new alert
           Alert appInfo = new Alert(Alert.AlertType.INFORMATION);
           appInfo.setResizable(false); // Disable resizing
           appInfo.setTitle("Cookbook"); // Set the title
           appInfo.setHeaderText(Help.about()); // Set the alert's text
           appInfo.show(); // Display the alert
        });

        /* ---Cookbook Logo--- */
        try {
            // Create a logo for this application
            Image image = new Image(new FileInputStream("src/main/CookbookLogo.png")); // Get the image
            ImageView imageView = new ImageView(image); // Create a viewable image node from the file
            // Position it
            imageView.setLayoutX(75);
            imageView.setLayoutY(400);
            // Resize it and preserve the ratio
            imageView.setFitHeight(200);
            imageView.setFitWidth(200);
            imageView.setPreserveRatio(true);
            pane.getChildren().add(imageView); // Add the image view to the parent pane
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        /* ---Four Primary Buttons--- */
        VBox vBox = new VBox();
        vBox.setSpacing(10); // Add some spacing in between the buttons
        // Create a button to add new recipes to the table
        Button addRecipeButton = new Button("Add");
        addRecipeButton.setMinSize(100, 55);
        addRecipeButton.setFocusTraversable(false);
        // Create a button to load a new cookbook
        Button loadCookbookButton = new Button("Load");
        loadCookbookButton.setMinSize(100, 55);
        loadCookbookButton.setFocusTraversable(false);
        // Create a button to create a brand-new cookbook
        Button newCookbookButton = new Button("New");
        newCookbookButton.setMinSize(100, 55);
        newCookbookButton.setFocusTraversable(false);
        // Create a button to delete the current cookbook
        Button deleteCookbookButton = new Button("Delete");
        deleteCookbookButton.setMinSize(100, 55);
        deleteCookbookButton.setFocusTraversable(false);
        // Position the vertical box pane
        vBox.setLayoutX(200);
        vBox.setLayoutY(100);
        // Add the primary buttons to the vertical box
        vBox.getChildren().addAll(addRecipeButton, loadCookbookButton, newCookbookButton, deleteCookbookButton);


        /* -----Recipes Table----- */
        // Create the table
        TableView<RecipeTableProperties> recipeTable = new TableView<>();
        recipeTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); // Disable overflow
        recipeTable.setPadding(new Insets(10, 10, 10, 10)); // Add a little padding
        // Position the table
        recipeTable.setLayoutX(350);
        recipeTable.setLayoutY(80);
        // Create a list to hold all the rows of the table
        ObservableList<RecipeTableProperties> recipeTableData = FXCollections.observableArrayList();
        recipeTable.setItems(recipeTableData); // Set the table's list to the one that was just created

        /* ---Create the Columns--- */
        // Create the First Column
        TableColumn<RecipeTableProperties, String> firstColumn = new TableColumn<>("Recipe Name");
        firstColumn.setMinWidth(200); // Set the column width
        firstColumn.setCellValueFactory(new PropertyValueFactory<>("recipeName")); // Retrieve the properties' value
        recipeTable.getColumns().add(firstColumn); // Add the column to the table

        // Create the Second Column
        TableColumn<RecipeTableProperties, String> secondColumn = new TableColumn<>("Difficulty Rating");
        secondColumn.setMinWidth(125);
        secondColumn.setCellValueFactory(new PropertyValueFactory<>("difficultyRating"));
        recipeTable.getColumns().add(secondColumn);

        // Create the Third Column
        TableColumn<RecipeTableProperties, String> thirdColumn = new TableColumn<>("");
        thirdColumn.setMinWidth(50);
        thirdColumn.setCellValueFactory(new PropertyValueFactory<>("openButton"));
        recipeTable.getColumns().add(thirdColumn);

        // Create the Fourth Column
        TableColumn<RecipeTableProperties, String> fourthColumn = new TableColumn<>("");
        fourthColumn.setMinWidth(50);
        fourthColumn.setCellValueFactory(new PropertyValueFactory<>("deleteButton"));
        recipeTable.getColumns().add(fourthColumn);


        /* -----Event Handlers----- */
        // On initial opening, prompt the user to either create a new cookbook or load a preexisting one
        Utilities.initialUserPrompt(recipeTableData, this.recipes, titleLabel);

        // Event Handler for the Add Button
        addRecipeButton.setOnAction(e -> {
            // Create popup with a text input for the user
            TextInputDialog recipeName = new TextInputDialog("Pancakes"); // 'Pancakes' is merely example text
            recipeName.setTitle("New Recipe"); // Set the title
            recipeName.setHeaderText(null); // Remove the header
            recipeName.setContentText("Please enter the recipe name:"); // Set the user prompt text

            // Get the response value
            Optional<String> recipeNameResult = recipeName.showAndWait();
            // Check if the user responded at all
            if (recipeNameResult.isPresent()) {

                // Create a list for the difficulty rating choices
                List<String> choices = new ArrayList<>();
                choices.add("*");
                choices.add("**");
                choices.add("***");

                // Create a new text popup with a menu for choices
                ChoiceDialog<String> difficultyRating = new ChoiceDialog<>("*", choices);
                difficultyRating.setTitle("New Recipe"); // Set the title
                difficultyRating.setHeaderText(null); // Remove the header
                difficultyRating.setContentText("How difficult is this dish to make: "); // Set the user prompt text

                // Get the response value
                Optional<String> difficultyRatingResult = difficultyRating.showAndWait();
                // Check if the user responded at all
                if (difficultyRatingResult.isPresent()){

                    // Create a file name
                    String fileName = "Cookbooks\\" + titleLabel.getText() + ".dat";

                    // Check if there is anything in the cookbook's table already
                    if (Utilities.deserializeObject(fileName) != null || !recipeTableData.isEmpty()) {
                        // Get the recipes already in the cookbook's table
                        this.recipes = (ArrayList<Recipe>) Utilities.deserializeObject(fileName);
                    }

                    // Create a new recipe
                    Recipe recipe = new Recipe(recipeNameResult.get(), difficultyRatingResult.get());
                    this.recipes.add(recipe); // Add the recipe to the recipe list

                    // Save the recipe within this cookbook file
                    Utilities.serializeObject(fileName, this.recipes);
                    Utilities.loadRecipes(recipeTableData, this.recipes); // Sort the new list and update the table

                }
            }
        });

        // Event Handler for the Load Button
        loadCookbookButton.setOnAction(e -> {
            Utilities.loadCookbook(recipeTableData, this.recipes, titleLabel, false);
        });

        // Event Handler for the New Button
        newCookbookButton.setOnAction(e -> {
            Utilities.newCookbook(recipeTableData, this.recipes, titleLabel, false);
        });

        // Event Handler for the Delete Button
        deleteCookbookButton.setOnAction(e -> {
            // Create a popup for the user to confirm the deletion
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Cookbook"); // Set title
            alert.setHeaderText("Are you sure you want to delete cookbook: " + titleLabel.getText()); // Set header

            // Create the two choices as buttons
            ButtonType confirmButton = new ButtonType("Confirm");
            ButtonType cancelButton = new ButtonType("Cancel");
            // Add the buttons to the popup
            alert.getButtonTypes().setAll(confirmButton, cancelButton);

            // Get the response value from the user
            Optional<ButtonType> response = alert.showAndWait();
            // Check if the user responded at all
            if (response.isPresent()) {
                // Check if the user confirmed the deletion
                if (response.get() == confirmButton) {
                    // Get the file name and create a file from it
                    String fileName = "Cookbooks\\" + titleLabel.getText() + ".dat";
                    File cookbookFile = new File(fileName);

                    // Attempt to delete the file
                    if (cookbookFile.delete()) {
                        // Create a popup to let the user know of the success
                        Alert successfulAttempt = new Alert(Alert.AlertType.INFORMATION);
                        successfulAttempt.setTitle("Success"); // Set title
                        successfulAttempt.setHeaderText("This cookbook has successfully been deleted!"); // Set header
                        successfulAttempt.showAndWait(); // Display it

                        primaryStage.hide(); // Hide the screen whilst the initial prompt is up
                        Utilities.initialUserPrompt(recipeTableData, this.recipes, titleLabel); // Prompt the user
                        primaryStage.show(); // Show the screen again
                    }
                    // The file deletion has failed
                    else {
                        // Create a popup to let the user know of the failure
                        Alert failedAttempt = new Alert(Alert.AlertType.ERROR);
                        failedAttempt.setTitle("Error"); // Set title
                        failedAttempt.setHeaderText("This cookbook has failed to delete!"); // Set header
                        failedAttempt.showAndWait(); // Display it
                    }
                }
            }
        });


        // Add all screen nodes to the parent pane
        pane.getChildren().addAll(titleLabel, helpButton, vBox, recipeTable);
        // Scene
        Scene scene = new Scene(pane);
        // Set the stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("Cookbook");
        primaryStage.setResizable(false); // Disable resizing
        primaryStage.show();
    }

    /** Main method to launch the application */
    public static void main(String[] args) {
        System.out.println(Help.about()); // Print a quick about message
	    Application.launch(); // Launch the app
    }

    
}
