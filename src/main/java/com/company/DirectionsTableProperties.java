// **********************************************************************************
// Class Name: DirectionsTableProperties
// Author: Ayhan Mehdiyev
// File: Cookbook/src/main/java/com/company/DirectionsTableProperties.java
// Description:
//              This class provides data for the columns of the TableView node
//              meant to hold directions, which can be found in the second screen.
// **********************************************************************************
package com.company;

import javafx.scene.control.Button;

public class DirectionsTableProperties {

    // Instance variables
    private String instruction; // The instructions on how to make the dish, on a step-by-step basis
    private final Button deleteButton; // The button to delete the row, located on the fourth column

    /** Construct a default Properties object */
    public DirectionsTableProperties() {
        this.instruction = "";
        this.deleteButton = new Button("X");
    }

    /** Construct a copy object */
    public DirectionsTableProperties(DirectionsTableProperties directionsTableProperties) {
        this.instruction = directionsTableProperties.getInstruction();
        this.deleteButton = directionsTableProperties.getDeleteButton();
    }

    /** Construct a Properties object with the direction and current step count */
    public DirectionsTableProperties(String instruction) {
        this.instruction = instruction;
        this.deleteButton = new Button("X");
    }

    /** Set the direction */
    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    /** Get the direction */
    public String getInstruction() {
        return this.instruction;
    }

    /** Get the delete button */
    public Button getDeleteButton() {
        return this.deleteButton;
    }

    /**
     * Convert this object to its most accurate representation as a String
     *
     * @return a String version of this object
     */
    @Override
    public String toString() {
        return "Instruction: " + this.instruction;
    }

}
