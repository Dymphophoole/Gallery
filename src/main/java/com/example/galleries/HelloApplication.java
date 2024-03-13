package com.example.galleries;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    // Declare class-level variables
    private Stage primaryStage;
    private StackPane fullImagePane;
    private int currentIndex = 0;

    // Arrays to store file paths for thumbnails and full-size images
    private final String[] thumbnailPaths = {
            "/Images/thumbnail.jpg",
            "/Images/thumbnail2.jpg",
            "/Images/thumbnail3.jpg",
            "/Images/thumbnail4.jpg",
            "/Images/thumbnail5.jpg",
            "/Images/thumbnail6.jpg"
    };
    private final String[] fullsizePaths = {
            "/Images/image.jpg",
            "/Images/image2.jpg",
            "/Images/image3.jpg",
            "/Images/image4.jpg",
            "/Images/image5.jpg",
            "/Images/image6.jpg"
    };

    // Main method to launch the JavaFX application
    public static void main(String[] args) {
        launch(args);
    }

    // Override the start method to set up the initial stage
    @Override
    public void start(Stage stage) {
        // Initialize the primary stage
        this.primaryStage = stage;
        primaryStage.setTitle("Image Gallery");

        // Create the full image pane
        fullImagePane = createFullImagePane();

        // Create the scene with the full image pane and set styles
        Scene scene = new Scene(fullImagePane, 600, 400);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        // Set the scene to the primary stage and show it
        primaryStage.setScene(scene);
        primaryStage.show();

        // Initially, display the thumbnails
        showThumbnails();
    }

    // Method to create the full image pane
    private StackPane createFullImagePane() {
        StackPane stackPane = new StackPane();
        stackPane.getStyleClass().add("full-image-pane");
        return stackPane;
    }

    // Method to show the thumbnails on the full image pane
    private void showThumbnails() {
        // Clear the current children of the full image pane
        fullImagePane.getChildren().clear();

        // Create the heading for thumbnails
        Text thumbnailsHeading = new Text("Thumbnails");
        thumbnailsHeading.getStyleClass().add("heading");

        // Create the grid of thumbnails
        GridPane gridPane = createThumbnailGrid();

        // Create a vertical box to contain the heading and grid
        VBox thumbnailsVBox = new VBox(thumbnailsHeading, gridPane);
        thumbnailsVBox.setAlignment(Pos.CENTER);

        // Add the vertical box to the full image pane
        fullImagePane.getChildren().add(thumbnailsVBox);
    }

    // Method to create the grid of thumbnails
    private GridPane createThumbnailGrid() {
        // Create a new grid pane and set alignment and gaps
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(20);
        gridPane.setVgap(20);

        // Loop to create thumbnail images and add them to the grid
        for (int i = 0; i < 6; i++) {
            Image thumbnailImage = new Image(getClass().getResource(thumbnailPaths[i]).toExternalForm());
            ImageView thumbnailImageView = new ImageView(thumbnailImage);
            thumbnailImageView.setFitWidth(200);
            thumbnailImageView.setFitHeight(150);

            StackPane thumbnailPane = new StackPane(thumbnailImageView);
            int finalI = i;
            thumbnailPane.setOnMouseClicked(event -> showFullImage(finalI));

            int row = i / 2;
            int col = i % 2;

            gridPane.add(thumbnailPane, col, row);
        }

        // Return the created grid pane
        return gridPane;
    }

    // Method to show the full image based on the selected thumbnail
    private void showFullImage(int index) {
        // Update the current index

        currentIndex = index;

        // Load the full-size image
        Image fullImage = new Image(getClass().getResource(fullsizePaths[currentIndex]).toExternalForm());
        ImageView fullImageView = new ImageView(fullImage);
        fullImageView.setFitWidth(700);
        fullImageView.setFitHeight(700);

        // Create a stack pane to center the full image
        StackPane fullImageStackPane = new StackPane(fullImageView);
        StackPane.setAlignment(fullImageStackPane, Pos.CENTER);

        // Create the heading for the full image
        Text fullImageHeading = new Text("Full Image " + (currentIndex + 1));
        fullImageHeading.getStyleClass().add("heading");

        // Create a vertical box to contain the heading and full image
        VBox fullImageWithHeading = new VBox(fullImageHeading, fullImageStackPane);
        fullImageWithHeading.setAlignment(Pos.CENTER);

        // Create the navigation buttons box
        HBox buttonsBox = createNavigationBox();

        // Create a vertical box to contain the heading, full image, and navigation buttons
        VBox fullImageContainer = new VBox(fullImageWithHeading, buttonsBox);
        fullImageContainer.setAlignment(Pos.CENTER);

        // Clear the current children of the full image pane and add the new content
        fullImagePane.getChildren().clear();
        fullImagePane.getChildren().add(fullImageContainer);
    }

    // Method to create the navigation buttons box
    private HBox createNavigationBox() {
        // Create Previous, Next, and Back buttons
        Button prevButton = new Button("Previous");
        Button nextButton = new Button("Next");
        Button backButton = new Button("Back");

        // Create a horizontal box to contain the buttons
        HBox navigationBox = new HBox(prevButton, nextButton, backButton);
        navigationBox.setAlignment(Pos.CENTER);
        navigationBox.setSpacing(10);

        // Set actions for button clicks
        prevButton.setOnAction(event -> showPreviousImage());
        nextButton.setOnAction(event -> showNextImage());
        backButton.setOnAction(event -> showThumbnails());

        // Return the created navigation box
        return navigationBox;
    }

    // Method to show the previous full-size image
    private void showPreviousImage() {
        if (currentIndex > 0) {
            currentIndex--;
            showFullImage(currentIndex);
        }
    }

    // Method to show the next full-size image
    private void showNextImage() {
        if (currentIndex < 5) {
            currentIndex++;
            showFullImage(currentIndex);
        }
    }
}
