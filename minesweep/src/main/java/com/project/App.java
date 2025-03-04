package com.project;


import java.security.PublicKey;
import java.util.Scanner;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;

public class App extends GameApplication {
    private static int GRID_SIZE_IN_CELLS = 4;
    private static int CELL_SIZE = 100;
    public static void main(String[] args) {
        launch(args);
        Minesweeper game = initMineFieldFromFile("minefield/minefield01.txt");
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Welcome to Minesweeper!");

        // Main game loop
        boolean gameOver = false;
        while (!gameOver) {
            game.displayField();
            System.out.println("Choose a row (1-9): ");
            int rowChoice = scanner.nextInt();
            System.out.println("Choose a column (1-9): ");
            int colChoice = scanner.nextInt();

            if (rowChoice < 1 || rowChoice > 9 || colChoice < 1 || colChoice > 9) {
                System.out.println("Invalid input. Please select both a row and a column between 1 and 9.");
                continue;
            }

            game.revealCell(rowChoice - 1, colChoice - 1);

            // Check if the game is over
            if (game.cells[rowChoice - 1][colChoice - 1] == Minesweeper.IS_MINE) {
                game.displayField();
                System.out.println("Game Over!");
                gameOver = true;  // End the game
            } else {
                System.out.println("You're safe.");
            }
        }

        scanner.close();
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
    GridPane grid = new GridPane();
    for (int y = 0; y < GRID_SIZE_IN_CELLS; y++) {
        for (int x = 0; x < GRID_SIZE_IN_CELLS; x++) {
            Cell cell = new Cell(x, y);
            grid.add(cell, x, y);
        }
    }
    
    FXGL.getGameScene().addUINode(grid);
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
            symbol.setText(Math.random() < 0.2 ? "💣" : "");
        }
    }


    static Minesweeper initMineField() {
        Minesweeper game = new Minesweeper(9, 9);
        game.setMineCell(0, 1);
        game.setMineCell(1, 5);
        game.setMineCell(1, 8);
        game.setMineCell(2, 4);
        game.setMineCell(3, 6);
        game.setMineCell(4, 2);
        game.setMineCell(5, 4);
        game.setMineCell(6, 2);
        game.setMineCell(7, 2);
        game.setMineCell(8, 6);
        return game;
    }

    static Minesweeper initMineFieldFromFile(String minefieldFile) {
        return new Minesweeper(minefieldFile);
    }



}
