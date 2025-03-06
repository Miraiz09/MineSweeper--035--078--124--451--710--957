package com.project;

import java.util.Scanner;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import javafx.scene.Scene;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.util.Duration;

public class App extends GameApplication {
    private static int GRID_SIZE_IN_CELLS = 5;
    private static int CELL_SIZE = 70;
    private static int score = 0;
    private static Text scoreText;
    private static int remainingCells = GRID_SIZE_IN_CELLS * GRID_SIZE_IN_CELLS;
    private static int numberOfMines; // Store the number of mines
    private static Minesweeper game;  // Instance of Minesweeper game

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(900);
        settings.setHeight(900);
        settings.setTitle("MineSweeper");
        settings.setVersion("0.3");
        settings.setMainMenuEnabled(true);
    }

    @Override
    protected void initGame() {
        createMinefieldUI();  // Create UI to enter number of mines
    }

    private void createMinefieldUI() {
        Pane root = new Pane();

        Text promptText = new Text("Enter Number of Mines:");
        promptText.setFont(Font.font(20));
        promptText.setFill(Color.BLACK);
        promptText.setTranslateX(320);
        promptText.setTranslateY(250);

        TextField mineInput = new TextField();
        mineInput.setPromptText("Number of Mines");
        mineInput.setTranslateX(320);
        mineInput.setTranslateY(300);

        Button startButton = new Button("Start Game");
        startButton.setTranslateX(320);
        startButton.setTranslateY(350);

        // Action when the start button is clicked
        startButton.setOnAction(e -> {
            try {
                numberOfMines = Integer.parseInt(mineInput.getText());  // Get the number of mines
                if (numberOfMines > 0 && numberOfMines <= GRID_SIZE_IN_CELLS * GRID_SIZE_IN_CELLS) {
                    startGame(numberOfMines);  // Start the game with the number of mines
                    root.getChildren().clear();  // Clear the input UI once game starts
                } else {
                    System.out.println("Please enter a valid number of mines.");
                }
            } catch (NumberFormatException ex) {
                System.out.println("Invalid number of mines.");
            }
        });

        root.getChildren().addAll(promptText, mineInput, startButton);
        FXGL.getGameScene().setBackgroundColor(Color.LIGHTGRAY);
        FXGL.getGameScene().addUINode(root);  // Add the UI to the game scene
    }

    private void startGame(int numberOfMines) {
        game = new Minesweeper(GRID_SIZE_IN_CELLS, GRID_SIZE_IN_CELLS);
        game.setRandomMines(numberOfMines);  // Set random mines in the minefield
        game.displayField();

        // Create the grid layout for the game
        int spacing = 40;
        GridPane grid = new GridPane();
        grid.setHgap(spacing);
        grid.setVgap(spacing);
        grid.setPadding(new Insets(spacing));
        for (int y = 0; y < GRID_SIZE_IN_CELLS; y++) {
            for (int x = 0; x < GRID_SIZE_IN_CELLS; x++) {
                Cell cell = new Cell(x, y);
                grid.add(cell, x, y);
            }
        }

        scoreText = new Text("Score: 0");
        scoreText.setFont(Font.font(18));
        scoreText.setFill(Color.BLACK);
        scoreText.setTranslateX(750);
        scoreText.setTranslateY(30);

        FXGL.getGameScene().addUINode(grid);
        FXGL.getGameScene().addUINode(scoreText);
    }

    private static class Cell extends StackPane {
        private int x;
        private int y;
        private boolean isFlipped = false;
        private Rectangle bg;
        private Text symbol;

        Cell(int x, int y) {
            this.x = x;
            this.y = y;

            setTranslateX(x * CELL_SIZE);
            setTranslateY(y * CELL_SIZE);

            bg = new Rectangle(CELL_SIZE, CELL_SIZE, Color.BLACK);
            bg.setStroke(Color.BLACK);
            symbol = new Text("");
            symbol.setStyle("-fx-font-size: 20px;");

            getChildren().addAll(bg, symbol);
            setOnMouseClicked(e -> reveal());
        }

        public void reveal() {
            if (isFlipped) return;
            isFlipped = true;
            bg.setFill(Color.WHITE);

            boolean isBomb = game.getCell(x, y) == Minesweeper.IS_MINE;  // Check if the cell has a mine
            symbol.setText(isBomb ? "💣" : "");

            if (isBomb) {
                score -= 10;
                System.out.println("HAH YOU GOT BOMB!");
            } else {
                score += 5;
                App.remainingCells--;
                scoreText.setText("Score: " + score);
                System.out.println("Score: " + score);
            }

            scoreText.setText("Score: " + score);

            if (score <= 0) {
                System.out.println("Boom! Game Over!");
                FXGL.runOnce(() -> App.showLoseScreen(), Duration.seconds(0.5));
            }

            // Check if the game is won (score > 30)
            if (score > 30) {
                FXGL.runOnce(() -> App.showWinScreen(), Duration.seconds(0.5));
            }
        }
    }

    // Show win message (YOU WIN)
    static void showWinScreen() {
        Text winText = new Text("YOU WIN!");
        winText.setFont(Font.font(50));
        winText.setFill(Color.GREEN);  // Set the color to green
        winText.setTextAlignment(TextAlignment.CENTER);

        winText.setTranslateX(900 / 2 - 100);
        winText.setTranslateY(900 / 2);

        FXGL.getGameScene().addUINode(winText);

        FXGL.runOnce(() -> {
            FXGL.getGameController().exit();
        }, Duration.seconds(5));
    }

    // Show lose message (YOU LOSE)
    static void showLoseScreen() {
        Text winText = new Text("YOU LOSE!");
        winText.setFont(Font.font(50));
        winText.setFill(Color.RED);  // Set the color to red
        winText.setTextAlignment(TextAlignment.CENTER);

        winText.setTranslateX(900 / 2 - 100);
        winText.setTranslateY(900 / 2);

        FXGL.getGameScene().addUINode(winText);

        FXGL.runOnce(() -> {
            FXGL.getGameController().exit();
        }, Duration.seconds(5));
    }
}
