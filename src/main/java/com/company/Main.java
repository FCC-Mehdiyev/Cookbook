// **********************************************************************************
// Title: Cookbook
// Class Name: Main
// Author: Ayhan Mehdiyev
// File: Cookbook/src/main/java/com/company/Main.java
// Description:
//              This is the main class where the screens of the application are created.
// **********************************************************************************
package com.company;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Callback;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;


public class Main extends Application {

    private ArrayList<Recipe> recipes = new ArrayList<Recipe>(); // An ArrayList to hold all current recipes

    @Override
    public void start(Stage stage) {
        this.initiateScreenOne(stage, true, null); // Start the application with the first screen
    }

    /**
     * Create and run the first screen of the Cookbook application
     *
     * @param stage the stage to place all nodes and scenes on
     * @param initialLaunch a boolean value to check if this is the first time the user is opening the application
     * @param cookbookTitle the name of the cookbook
     */
    private void initiateScreenOne(Stage stage, boolean initialLaunch, String cookbookTitle) {
        // Create a pane that will act as a parent pane and hold all nodes
        Pane parentPane = new Pane();
        parentPane.setMinSize(900, 650);

        // Create a title label for the cookbook
        Label titleLabel = Utilities.createLabel("", 40, 45, 15);

        // Create a help button
        Button helpButton = Utilities.createButton("?", FontWeight.EXTRA_BOLD, 15, "#3793b4", 25, 25, 854, 10);
        // Event handler for the button press
        helpButton.setOnAction(e -> {
            // Create a new alert that contains a message about what the application is
            Alert appInfo = Utilities.createAlert(Alert.AlertType.INFORMATION, "Cookbook", Utilities.about(), null);
            appInfo.show();
        });

        // Create an imageview to display the application logo
        try {
            // Retrieve the image from the file path
            javafx.scene.image.Image image = new javafx.scene.image.Image(new FileInputStream("src/main/CookbookLogo.png"));
            PixelReader reader = image.getPixelReader();
            // Get the width and height of the image
            int imageWidth = (int) image.getWidth();
            int imageHeight = (int) image.getHeight();
            WritableImage wImage = new WritableImage(imageWidth, imageHeight);
            PixelWriter writer = wImage.getPixelWriter();
            // Iterate through every pixel of the image
            for(int y = 0; y < imageHeight; y++) {
                for(int x = 0; x < imageWidth; x++) {
                    // Invert each pixel color
                    Color color = reader.getColor(x, y);
                    writer.setColor(x, y, color.invert());
                }
            }
            // Create an image view out of the modified image
            ImageView imageView = new ImageView(wImage);
            // Position it
            imageView.setLayoutX(10);
            imageView.setLayoutY(430);
            // Resize it and preserve the ratio
            imageView.setFitHeight(250);
            imageView.setFitWidth(250);
            imageView.setPreserveRatio(true);
            parentPane.getChildren().add(imageView);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Create a vertical box to hold primary buttons
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(5, 5, 5, 5));
        vBox.setSpacing(10);
        vBox.setLayoutX(90);
        vBox.setLayoutY(80);

        // Create a button that will add new recipes to the table
        Button addRecipeButton = Utilities.createButton("Add Recipe", FontWeight.NORMAL, 10, "#36257b", 115, 55, 0, 0);
        // Create a button that will load a new cookbook
        Button loadCookbookButton = Utilities.createButton("Load Cookbook", FontWeight.NORMAL, 10, "#363b87", 115, 55, 0, 0);
        // Create a button that will create a new cookbook
        Button newCookbookButton = Utilities.createButton("New Cookbook", FontWeight.NORMAL, 10, "#36448b", 115, 55, 0, 0);
        // Create a button that will delete the current cookbook
        Button deleteCookbookButton = Utilities.createButton("Delete Cookbook", FontWeight.NORMAL, 10, "#364c8f", 115, 55, 0, 0);
        // Create a button to view all saved cookbooks
        Button viewCookbooksButton = Utilities.createButton("View Cookbooks", FontWeight.NORMAL, 10, "#365293",115, 55, 0, 0);
        // Event handler for the button press
        viewCookbooksButton.setOnAction(e -> {
            // Create a new alert that contains a message with the list of cookbooks
            Alert cookbookList = Utilities.createAlert(Alert.AlertType.INFORMATION, "Cookbook", Utilities.getCookbooks(), null);
            cookbookList.show();
        });


        // Create a TableView for recipes
        TableView<RecipeTableProperties> recipeTable = (TableView<RecipeTableProperties>) Utilities.createTableView(225, 80);
        recipeTable.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-border-color: transparent;"
        );
        // Create a list to hold all the rows of the table
        ObservableList<RecipeTableProperties> recipeTableData = FXCollections.observableArrayList();
        recipeTable.setItems(recipeTableData);

        // Create the first column for the table
        TableColumn<RecipeTableProperties, String> recipeColOne = (TableColumn<RecipeTableProperties, String>) Utilities.createTableColumn("Recipe Name", 200, 250, "recipeName");
        recipeTable.getColumns().add(recipeColOne);
        // Create the second column for the table
        TableColumn<RecipeTableProperties, String> recipeColTwo = (TableColumn<RecipeTableProperties, String>) Utilities.createTableColumn("Difficulty Rating", 110, 125, "difficultyRating");
        recipeTable.getColumns().add(recipeColTwo);
        // Create the Third Column
        TableColumn<RecipeTableProperties, String> recipeColThree = (TableColumn<RecipeTableProperties, String>) Utilities.createTableColumn("Total Time (hr)", 90, 100, "totalTime");
        recipeTable.getColumns().add(recipeColThree);
        // Create the Forth Column
        TableColumn<RecipeTableProperties, String> recipeColFour = (TableColumn<RecipeTableProperties, String>) Utilities.createTableColumn("Servings", 70, 80, "servings");
        recipeTable.getColumns().add(recipeColFour);
        // Create the Fifth Column
        TableColumn<RecipeTableProperties, Button> recipeColFive = (TableColumn<RecipeTableProperties, Button>) Utilities.createTableColumn("", 35, 50, "openButton");
        recipeTable.getColumns().add(recipeColFive);
        // Create the Sixth Column
        TableColumn<RecipeTableProperties, Button> recipeColSix = (TableColumn<RecipeTableProperties, Button>) Utilities.createTableColumn("", 30, 50, "deleteButton");
        recipeTable.getColumns().add(recipeColSix);

        // On initial opening, prompt the user to either create a new cookbook or load a preexisting one
        // However, make sure this is the initial launch of the application and not going back to
        // this screen from the second screen
        if (initialLaunch) {
            // Attempt to fill the recipes list
            this.recipes = Utilities.initialUserPrompt(recipeTableData, this.recipes, titleLabel);
            // If it fails, try to fill the list through deserializing the saved object
            if (this.recipes.size() < 1) {
                String fileName = "Cookbooks\\" + titleLabel.getText() + ".dat";
                if (Utilities.deserializeObject(fileName) != null)
                    this.recipes = (ArrayList<Recipe>) Utilities.deserializeObject(fileName);
            }
        }
        else {
            titleLabel.setText(cookbookTitle);
            Utilities.loadRecipes(recipeTableData, this.recipes); // Sort the new list and update the table
        }

        // Event handler for the open button (fifth column)
        Callback<TableColumn<RecipeTableProperties, Button>, TableCell<RecipeTableProperties, Button>> fifthColumnCellFactory = new Callback<>() {
            @Override
            public TableCell<RecipeTableProperties, Button> call(TableColumn<RecipeTableProperties, Button> recipeTablePropertiesStringTableColumn) {
                return new TableCell<>() {
                    final Button button = new Button("|>");
                    {
                        // Action handler
                        button.setOnAction(e -> {
                            // Retrieve the recipe that the user has opened
                            Recipe recipe = recipes.get(Utilities.findRecipe(recipes, new Recipe(recipeTableData.get(getIndex()).getRecipeName(), recipeTableData.get(getIndex()).getDifficultyRating(),
                                    Double.parseDouble(recipeTableData.get(getIndex()).getTotalTime()), Integer.parseInt(recipeTableData.get(getIndex()).getServings()))));
                            // Run the second screen
                            initiateScreenTwo(stage, recipe, titleLabel.getText());
                        });
                    }

                    @Override
                    public void updateItem(Button item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty)
                            setGraphic(null);
                        else
                            setGraphic(button);
                        setText(null);
                    }
                };
            }
        };
        recipeColFive.setCellFactory(fifthColumnCellFactory); // Link the handler to the button

        // Event handler for the delete button (sixth column)
        Callback<TableColumn<RecipeTableProperties, Button>, TableCell<RecipeTableProperties, Button>> sixthColumnCellFactory = new Callback<>() {
            @Override
            public TableCell<RecipeTableProperties, Button> call(TableColumn<RecipeTableProperties, Button> recipeTablePropertiesStringTableColumn) {
                return new TableCell<>() {
                    final Button button = new Button("X");
                    {
                        // Action handler
                        button.setOnAction(e -> {
                            // Remove the recipe from the recipes ArrayList
                            recipes.remove(Utilities.findRecipe(recipes, new Recipe(recipeTableData.get(getIndex()).getRecipeName(), recipeTableData.get(getIndex()).getDifficultyRating(),
                                    Double.parseDouble(recipeTableData.get(getIndex()).getTotalTime()), Integer.parseInt(recipeTableData.get(getIndex()).getServings()))));
                            // Remove it visually from the RecipeTableView
                            recipeTable.getItems().remove(getIndex());
                            // Create a file name and save the cookbook's new list of recipes
                            String fileName = "Cookbooks\\" + titleLabel.getText() + ".dat";
                            Utilities.serializeObject(fileName, recipes);
                        });
                    }

                    @Override
                    public void updateItem(Button item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty)
                            setGraphic(null);
                        else
                            setGraphic(button);
                        setText(null);
                    }
                };
            }
        };
        recipeColSix.setCellFactory(sixthColumnCellFactory); // Link the handler to the button


        // Event Handler for the Add Button
        addRecipeButton.setOnAction(e -> {

            // Create a text popup with user input
            TextInputDialog recipeName = Utilities.createTextInputDialog("Pancakes", "New Recipe", "Please enter the recipe name:");
            // Get the response value
            Optional<String> recipeNameResult = recipeName.showAndWait();
            // Check if the user responded at all
            if (recipeNameResult.isPresent()) {

                // Create a text popup with user input
                TextInputDialog totalTime = Utilities.createTextInputDialog("3.6", "New Recipe", "Please enter the new total time (hours):");
                // Get the response value
                Optional<String> totalTimeResult = totalTime.showAndWait();
                // Check if the user responded at all
                if (totalTimeResult.isPresent()) {

                    // Create a text popup with user input
                    TextInputDialog servings = Utilities.createTextInputDialog("8", "New Recipe", "Please enter the servings:");
                    // Get the response value
                    Optional<String> servingsResult = servings.showAndWait();
                    // Check if the user responded at all
                    if (servingsResult.isPresent()) {

                        // Create a text popup with a menu of choices that the user can pick from
                        ChoiceDialog<String> difficultyRating = Utilities.createChoiceDialog(Arrays.asList("*", "**", "***"), "*", "New Recipe", "How difficult is this dish to make:");

                        // Get the response value
                        Optional<String> difficultyRatingResult = difficultyRating.showAndWait();
                        // Check if the user responded at all
                        if (difficultyRatingResult.isPresent()) {

                            // Create a new recipe based off what the user inputted
                            Recipe recipe = new Recipe(recipeNameResult.get(), difficultyRatingResult.get(), Double.parseDouble(totalTimeResult.get()), Integer.parseInt(servingsResult.get()));

                            // Make sure this recipe isn't already in the cookbook
                            for (Recipe currRecipe : this.recipes) {
                                if (currRecipe.getName().equals(recipe.getName()) && currRecipe.getDifficultyRating().equals(recipe.getDifficultyRating()) && currRecipe.getTotalTime() == recipe.getTotalTime() && currRecipe.getServings() == recipe.getServings()) {
                                    // Display a popup to the user to let the user know not to create duplicate recipes
                                    Alert failedAttempt = Utilities.createAlert(Alert.AlertType.ERROR, "Error", "This cookbook already contains the recipe you are trying to create!", null);
                                    failedAttempt.showAndWait();
                                    return; // The recipe is already in the cookbook, so get out of the process
                                }
                            }

                            // Create a file name
                            String fileName = "Cookbooks\\" + titleLabel.getText() + ".dat";
                            // Check if there is anything in the cookbook's table already
                            if (Utilities.deserializeObject(fileName) != null || !recipeTableData.isEmpty())
                                // Get the recipes already in the cookbook's table
                                this.recipes = (ArrayList<Recipe>) Utilities.deserializeObject(fileName);

                            // Add the recipe to the list
                            this.recipes.add(recipe);

                            // Save the recipe within this cookbook file
                            Utilities.serializeObject(fileName, this.recipes);
                            Utilities.loadRecipes(recipeTableData, this.recipes);
                        }
                    }
                }
            }
        });

        // Event Handler for the Load Button
        loadCookbookButton.setOnAction(e -> {
            this.recipes = Utilities.loadCookbook(recipeTableData, this.recipes, titleLabel, false);
        });

        // Event Handler for the New Button
        newCookbookButton.setOnAction(e -> {
            Utilities.newCookbook(recipeTableData, this.recipes, titleLabel, false);
        });

        // Event Handler for the Delete Button
        deleteCookbookButton.setOnAction(e -> {
            // Create a popup for the user to confirm the deletion
            Alert confirmDeletion = Utilities.createAlert(Alert.AlertType.CONFIRMATION, "Delete Cookbook", "Are you sure you want to delete cookbook: " + titleLabel.getText(), null);

            // Create the two choices as buttons
            ButtonType confirmButton = new ButtonType("Confirm");
            ButtonType cancelButton = new ButtonType("Cancel");
            // Add the buttons to the popup
            confirmDeletion.getButtonTypes().setAll(confirmButton, cancelButton);

            // Get the response value from the user
            Optional<ButtonType> response = confirmDeletion.showAndWait();
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
                        Alert successfulAttempt = Utilities.createAlert(Alert.AlertType.INFORMATION, "Success", "This cookbook has successfully been deleted!", null);
                        successfulAttempt.showAndWait(); // Display it
                        stage.hide(); // Hide the screen whilst the initial prompt is u
                        this.recipes = Utilities.initialUserPrompt(recipeTableData, this.recipes, titleLabel); // Prompt the user
                        stage.show(); // Show the screen again
                    }
                    // The file deletion has failed
                    else {
                        // Create a popup to let the user know of the failure
                        Alert failedAttempt = Utilities.createAlert(Alert.AlertType.ERROR, "Error", "This cookbook has failed to delete!", null);
                        failedAttempt.showAndWait(); // Display it
                    }
                }
            }
        });

        // Add the primary buttons to the vertical box
        vBox.getChildren().addAll(addRecipeButton, loadCookbookButton, newCookbookButton, deleteCookbookButton, viewCookbooksButton);
        // Add all screen nodes to the parent pane
        parentPane.getChildren().addAll(titleLabel, helpButton, vBox, recipeTable);

        // Set the background of the screen to a gradient
        parentPane.setBackground(new Background(new BackgroundFill(new LinearGradient(
                0, 0, 1, 1, true,
                CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#36096d")),
                new Stop(1, Color.web("#37d5d6"))),
                CornerRadii.EMPTY,
                Insets.EMPTY))
        );


        // Create a scene to hold the parent pane
        Scene scene = new Scene(parentPane);
        // Set up the stage
        stage.setScene(scene);
        stage.setTitle("Cookbook");
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Create and run the second screen of the Cookbook application
     *
     * @param stage the stage to place all nodes and scenes on
     * @param recipe the specific recipe that the user wants to open
     * @param cookbookTitle the name of the cookbook to be used when going back to the first screen
     */
    private void initiateScreenTwo(Stage stage, Recipe recipe, String cookbookTitle) {
        // Create a pane that will act as a parent pane and hold all nodes
        Pane parentPane = new Pane();
        parentPane.setMinSize(900, 650);

        // Create a title label for the recipe
        Label titleLabel = Utilities.createLabel(recipe.getName() + "\nTotal Time: " + recipe.getTotalTime() +
                " Hours\nServings: " + recipe.getServings() + "\nDifficulty Rating: " + recipe.getDifficultyRating(), 30, 275,  25);
        titleLabel.setTextAlignment(TextAlignment.CENTER); // Center align the text

        // Create the back button to allow the user to go back to the first screen
        Button backButton = Utilities.createButton("Back", FontWeight.NORMAL,10, "#36267c", 100, 55, 50, 25);
        // Event handler for the back button
        backButton.setOnAction(e -> {
            // Go back to the first screen
            this.initiateScreenOne(stage, false, cookbookTitle);
        });

        // Create the edit button to allow the user to change some recipe details
        Button editButton = Utilities.createButton("Edit", FontWeight.NORMAL, 10, "#3790b2", 100, 55, 750, 25);
        // Event handler for the edit button
        editButton.setOnAction(e -> {

            // Create and display a popup to allow the user to enter the new recipe name
            TextInputDialog newRecipeName = Utilities.createTextInputDialog("Ultra Pancakes", "Edit Recipe", "Please enter the new recipe name:");
            // Get the response value
            Optional<String> newRecipeNameResult = newRecipeName.showAndWait();
            // Check if the user responded at all
            if (newRecipeNameResult.isPresent()) {

                // Create and display a popup to allow the user to enter the new total time
                TextInputDialog newTotalTime = Utilities.createTextInputDialog("2", "Edit Recipe", "Please enter the new total time (hours):");
                // Get the response value
                Optional<String> newTotalTimeResult = newTotalTime.showAndWait();
                // Check if the user responded at all
                if (newTotalTimeResult.isPresent()) {

                    // Create and display a popup to allow the user to enter the new total time
                    TextInputDialog newServings = Utilities.createTextInputDialog("16", "Edit Recipe", "Please enter the new servings:");
                    // Get the response value
                    Optional<String> newServingsResult = newServings.showAndWait();
                    // Check if the user responded at all
                    if (newServingsResult.isPresent()) {

                        // Create a text popup with a menu of choices that the user can pick from
                        ChoiceDialog<String> newDifficultyRating = Utilities.createChoiceDialog(Arrays.asList("*", "**", "***"), "*", "Edit Recipe", "How difficult is this dish to make:");

                        // Get the response value
                        Optional<String> newDifficultyRatingResult = newDifficultyRating.showAndWait();
                        // Check if the user responded at all
                        if (newDifficultyRatingResult.isPresent()) {

                            // Update the recipe
                            recipe.setName(newRecipeNameResult.get());
                            recipe.setDifficultyRating(newDifficultyRatingResult.get());
                            recipe.setTotalTime(Double.parseDouble(newTotalTimeResult.get()));
                            recipe.setServings(Integer.parseInt(newServingsResult.get()));

                            // Update the title of the current recipe screen
                            titleLabel.setText(recipe.getName() + "\nTotal Time: " + recipe.getTotalTime() +
                                    " Hours\nServings: " + recipe.getServings() + "\nDifficulty Rating: " + recipe.getDifficultyRating());

                            // Create a file name
                            String fileName = "Cookbooks\\" + cookbookTitle + ".dat";
                            // Save the updated recipe
                            Utilities.serializeObject(fileName, this.recipes);
                        }
                    }
                }
            }
        });

        // Create the PDF button to allow the user to save the current recipe as a PDF
        Button pdfButton = Utilities.createButton("PDF", FontWeight.NORMAL, 10, "#3790b2", 100, 55, 750, 90);
        // Event handler for the PDF button
        pdfButton.setOnAction(e -> {

            try {

                // Create the PDF document
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream("PDFs/" + recipe.getName() + ".pdf"));

                // Create the needed fonts
                Font infoFont = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
                Font listFont = FontFactory.getFont(FontFactory.COURIER, 12, BaseColor.DARK_GRAY);

                // Create a paragraph to display which cookbook the recipe is from
                Paragraph cookbook = new Paragraph("Cookbook: " + cookbookTitle, infoFont);
                cookbook.setAlignment(Element.ALIGN_CENTER);

                // Get the cookbook logo, then resize it to make it smaller
                BufferedImage originalImage = ImageIO.read(new FileInputStream("src/main/CookbookLogo.png"));
                java.awt.Image resizedImage = originalImage.getScaledInstance(150, 119, java.awt.Image.SCALE_SMOOTH);
                com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance(resizedImage, null);
                image.setAlignment(Element.ALIGN_CENTER);

                // Create a paragraph chunk to contain all the recipe's information
                Paragraph recipeInfo = new Paragraph(
                        "Recipe Name: " + recipe.getName() +
                                "\nDifficulty Rating: " + recipe.getDifficultyRating() +
                                "\nTotal Hours: " + recipe.getTotalTime() +
                                "\nServings: " + recipe.getServings() + "\n\n\n", infoFont
                );
                recipeInfo.setAlignment(Element.ALIGN_CENTER);

                // Create a header for the ingredients
                Paragraph ingredientHeader = new Paragraph("Ingredients:", infoFont);
                ingredientHeader.setAlignment(Element.ALIGN_LEFT);

                // Create a paragraph chunk for the ingredients
                Paragraph ingredients = new Paragraph("", listFont);
                ingredients.setAlignment(Element.ALIGN_LEFT);
                ingredients.setIndentationLeft(75f);
                // Add every ingredient, its amount, and its units to the paragraph
                int counter = 1;
                for (Map.Entry<String, IngredientUnits> map : recipe.getIngredientsList().entrySet()) {
                    ingredients.add(counter + ". " + map.getKey() + " (" + map.getValue().getAmount() + ", " + map.getValue().getUnits() + ")\n");
                    counter++;
                }

                // Create a header for the directions
                Paragraph directionHeader = new Paragraph("Directions:", infoFont);
                directionHeader.setAlignment(Element.ALIGN_LEFT);

                // Create a paragraph chunk for the directions
                Paragraph directions = new Paragraph("", listFont);
                directions.setAlignment(Element.ALIGN_LEFT);
                directions.setIndentationLeft(75f);
                // Add every direction to the paragraph
                counter = 1;
                for (String direction : recipe.getDirectionsList()) {
                    directions.add(counter + ". " + direction + "\n");
                    counter++;
                }

                // Open the document to add the info
                document.open();

                // Add all the recipe information to the document
                document.add(cookbook);
                document.add(image);
                document.add(recipeInfo);
                document.add(ingredientHeader);
                document.add(ingredients);
                document.add(directionHeader);
                document.add(directions);

                // Close the document
                document.close();

                // Get the PDF path
                Path path = Paths.get("PDFs/" + recipe.getName() + ".pdf");
                // Display what happened to the user and show the location of the PDF
                Alert alert = Utilities.createAlert(Alert.AlertType.INFORMATION, "Recipe Saved!", "This recipe has been saved as a PDF!", "The PDF file can be found at: " + path);
                alert.showAndWait();

                // Open the PDF for the user to see
                File file = path.toFile();
                if (Desktop.isDesktopSupported()) {
                    Desktop desktop = Desktop.getDesktop();
                    if (file.exists())
                        desktop.open(file);
                }


            } catch (DocumentException | IOException ex) {
                ex.printStackTrace();
            }

     });


            // Create a new TableView to hold the ingredients
        TableView<IngredientsTableProperties> ingredientsTable = (TableView<IngredientsTableProperties>) Utilities.createTableView(25, 200);
        ingredientsTable.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-border-color: transparent;"
        );
        // Create a list to hold all the rows of the table
        ObservableList<IngredientsTableProperties> ingredientsTableData = FXCollections.observableArrayList();
        ingredientsTable.setItems(ingredientsTableData); // Set the table's list to the one that was just created

        // Create the first column for the table
        TableColumn<IngredientsTableProperties, String> ingredientColOne = (TableColumn<IngredientsTableProperties, String>) Utilities.createTableColumn("Ingredient", 150, 150, "ingredientName");
        ingredientsTable.getColumns().add(ingredientColOne); // Add the column to the table
        // Create the second column for the table
        TableColumn<IngredientsTableProperties, String> ingredientColTwo = (TableColumn<IngredientsTableProperties, String>) Utilities.createTableColumn("Amount", 80, 90, "ingredientAmount");
        ingredientsTable.getColumns().add(ingredientColTwo);
        // Create the third column for the table
        TableColumn<IngredientsTableProperties, String> ingredientColThree = (TableColumn<IngredientsTableProperties, String>) Utilities.createTableColumn("Units", 80, 100, "measuringUnits");
        ingredientsTable.getColumns().add(ingredientColThree);
        // Create the fourth column for the table
        TableColumn<IngredientsTableProperties, Button> ingredientColFour = (TableColumn<IngredientsTableProperties, Button>) Utilities.createTableColumn("", 35, 50, "deleteButton");
        ingredientsTable.getColumns().add(ingredientColFour);
        // Load any preexisting ingredients into the table
        Utilities.loadIngredients(ingredientsTableData, recipe);

        // Event handler for the delete button (fourth column)
        Callback<TableColumn<IngredientsTableProperties, Button>, TableCell<IngredientsTableProperties, Button>> fourthColumnCellFactory = new Callback<>() {
            @Override
            public TableCell<IngredientsTableProperties, Button> call(TableColumn<IngredientsTableProperties, Button> ingredientsTablePropertiesButtonTableColumn) {
                return new TableCell<>() {
                    final Button button = new Button("X");
                    {
                        // Action handler
                        button.setOnAction(e -> {
                            // Remove the ingredient from the ingredients list of the recipe
                            recipe.getIngredientsList().remove(ingredientsTableData.get(getIndex()).getIngredientName());
                            // Remove it visually from the IngredientsTableView
                            ingredientsTable.getItems().remove(getIndex());
                            // Create a file name and save the cookbook's new list of recipes
                            String fileName = "Cookbooks\\" + cookbookTitle + ".dat";
                            Utilities.serializeObject(fileName, recipes);
                        });
                    }

                    @Override
                    public void updateItem(Button item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty)
                            setGraphic(null);
                        else
                            setGraphic(button);
                        setText(null);
                    }
                };
            }
        };
        ingredientColFour.setCellFactory(fourthColumnCellFactory); // Link the handler to the button



        // Create a new TableView to hold the directions
        TableView<DirectionsTableProperties> directionsTable = (TableView<DirectionsTableProperties>) Utilities.createTableView(535, 200);
        directionsTable.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-border-color: transparent;"
        );
        // Create a list to hold all the rows of the table
        ObservableList<DirectionsTableProperties> directionsTableData = FXCollections.observableArrayList();
        directionsTable.setItems(directionsTableData); // Set the table's list to the one that was just created

        // Create the first column for the table
        TableColumn<DirectionsTableProperties, String> directionColOne = (TableColumn<DirectionsTableProperties, String>) Utilities.createTableColumn("Instruction", 200, 300, "instruction");
        directionsTable.getColumns().add(directionColOne);
        // Create the second column for the table
        TableColumn<DirectionsTableProperties, Button> directionColTwo = (TableColumn<DirectionsTableProperties, Button>) Utilities.createTableColumn("", 25, 50, "deleteButton");
        directionsTable.getColumns().add(directionColTwo);
        // Load any preexisting ingredients into the table now
        Utilities.loadDirections(directionsTableData, recipe); // Load the directions into the table
        // Event handler for the delete button (second column)
        Callback<TableColumn<DirectionsTableProperties, Button>, TableCell<DirectionsTableProperties, Button>> secondColumnCellFactory = new Callback<>() {
            @Override
            public TableCell<DirectionsTableProperties, Button> call(TableColumn<DirectionsTableProperties, Button> directionsTablePropertiesButtonTableColumn) {
                return new TableCell<>() {
                    final Button button = new Button("X");
                    {
                        // Action handler
                        button.setOnAction(e -> {
                            // Remove the direction from the directions list of the recipe
                            recipe.getDirectionsList().remove(directionsTableData.get(getIndex()).getInstruction());
                            // Remove it visually from the DirectionsTableView
                            directionsTable.getItems().remove(getIndex());
                            // Create a file name and save the cookbook's new list of recipes
                            String fileName = "Cookbooks\\" + cookbookTitle + ".dat";
                            Utilities.serializeObject(fileName, recipes);
                        });
                    }

                    @Override
                    public void updateItem(Button item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty)
                            setGraphic(null);
                        else
                            setGraphic(button);
                        setText(null);
                    }
                };
            }
        };
        directionColTwo.setCellFactory(secondColumnCellFactory); // Link the handler to the button


        // Create a button to add entries to the ingredients table
        Button newIngredientButton = Utilities.createButton("New Ingredient", FontWeight.NORMAL, 10, "#3675a5", 60, 40, 175, 605);
        // Event handler for the button press
        newIngredientButton.setOnAction(e -> {

            // Create popup with a text input for the user to enter the name of the ingredient
            TextInputDialog ingredientName = Utilities.createTextInputDialog("Butter", "New Ingredient", "Please enter the ingredient name:");
            // Get the response value
            Optional<String> ingredientNameResult = ingredientName.showAndWait();
            // Check if the user responded at all
            if (ingredientNameResult.isPresent()) {

                // Create a list for the measuring unit choices
                List<Enum<IngredientUnits.MeasuringUnits>> choices = new ArrayList<>(Arrays.asList(IngredientUnits.MeasuringUnits.values()));

                // Create a new text popup with a menu for choices
                ChoiceDialog<Enum<IngredientUnits.MeasuringUnits>> measuringUnits = new ChoiceDialog<>(IngredientUnits.MeasuringUnits.TEASPOONS, choices);
                measuringUnits.setTitle("New Ingredient");
                measuringUnits.setHeaderText(null);
                measuringUnits.setContentText("Select the measuring units for this ingredient:");
                // Get the response value
                Optional<Enum<IngredientUnits.MeasuringUnits>> measuringUnitsResult = measuringUnits.showAndWait();
                // Check if the user responded at all
                if (measuringUnitsResult.isPresent()) {

                    // Create popup with a text input for the user to enter the amount
                    TextInputDialog ingredientAmount = Utilities.createTextInputDialog("1.5", "New Ingredient", "Please enter the amount of the ingredient:");
                    // Get the response value
                    Optional<String> ingredientAmountResult = ingredientAmount.showAndWait();
                    // Check if the user responded at all
                    if (ingredientAmountResult.isPresent()) {

                        // Create a file name
                        String fileName = "Cookbooks\\" + cookbookTitle + ".dat";

                        // Add the ingredient to the ingredient list of the recipe
                        recipe.getIngredientsList().put(ingredientNameResult.get(), new IngredientUnits(Double.parseDouble(ingredientAmountResult.get()), measuringUnitsResult.get()));

                        // Save the recipe within this cookbook file
                        Utilities.serializeObject(fileName, this.recipes);
                        Utilities.loadIngredients(ingredientsTableData, recipe); // Load the ingredients into the table
                    }
                }
            }
        });


        // Create a button to add entries to the directions table
        Button newDirectionButton = Utilities.createButton("New Direction", FontWeight.NORMAL, 10, "#37bdc9", 60, 40, 650, 605);
        // Event handler for the button press
        newDirectionButton.setOnAction(e -> {

            // Create popup with a text input for the user to enter the instruction
            TextInputDialog instruction = Utilities.createTextInputDialog("Preheat oven to 350F", "New Direction", "Please enter the instruction:");
            // Get the response value
            Optional<String> instructionResult = instruction.showAndWait();
            // Check if the user responded at all
            if (instructionResult.isPresent()) {

                // Create a file name
                String fileName = "Cookbooks\\" + cookbookTitle + ".dat";

                // Add the direction to the direction list of the recipe
                recipe.getDirectionsList().add(instructionResult.get());

                // Save the recipe within this cookbook file
                Utilities.serializeObject(fileName, this.recipes);
                Utilities.loadDirections(directionsTableData, recipe); // Load the directions into the table
            }
        });

        // Add all nodes to the parent pane
        parentPane.getChildren().addAll(titleLabel, backButton, editButton, pdfButton, ingredientsTable, directionsTable, newIngredientButton, newDirectionButton);

        // Set the background of the screen to a gradient
        parentPane.setBackground(new Background(new BackgroundFill(new LinearGradient(
                0, 0, 1, 1, true,
                CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#36096d")),
                new Stop(1, Color.web("#37d5d6"))),
                CornerRadii.EMPTY,
                Insets.EMPTY))
        );

        // Create a scene to hold the parent pane
        Scene scene = new Scene(parentPane);
        // Set up the stage
        stage.setScene(scene);
        stage.setTitle("Cookbook");
        stage.setResizable(false); // Disable resizing
        stage.show();
    }

}
