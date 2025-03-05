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
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class App extends GameApplication {
    private static int GRID_SIZE_IN_CELLS = 5;
    private static int CELL_SIZE = 70;
    private static int score = 0;
    private static Text scoreText;
    private static int remainingCells = GRID_SIZE_IN_CELLS * GRID_SIZE_IN_CELLS;
    
    
    
    public static void main(String[] args) {
        launch(args);

        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Welcome to Minesweeper!");

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

private static void showWinScreen() {
    Text winText = new Text("YOU WIN!");
    winText.setFont(Font.font(50));
    winText.setFill(Color.RED);
    winText.setTextAlignment(TextAlignment.CENTER);
    
    winText.setTranslateX(900 / 2 - 100);
    winText.setTranslateY(900 / 2);

    FXGL.getGameScene().addUINode(winText);

    FXGL.runOnce(() -> {
        FXGL.getGameController().exit();
    }, Duration.seconds(5));
}

private static void showLoseScreen() {
    Text winText = new Text("YOU LOSE!");
    winText.setFont(Font.font(50));
    winText.setFill(Color.RED);
    winText.setTextAlignment(TextAlignment.CENTER);

    winText.setTranslateX(900 / 2 - 100);
    winText.setTranslateY(900 / 2);

    FXGL.getGameScene().addUINode(winText);

    FXGL.runOnce(() -> {
        FXGL.getGameController().exit();
    }, Duration.seconds(5));
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

        /*public void reveal() {
        if (isFlipped) return;
        isFlipped = true;
        bg.setFill(Color.WHITE);
        
        symbol.setText(Math.random() < 0.2 ? "💣" : "");
        

        
    }
    }*/
    public void reveal() {
        if (isFlipped) return;
        isFlipped = true;
        bg.setFill(Color.WHITE);
    
        boolean isBomb = Math.random() < 0.5; // 10% chance of a bomb
        symbol.setText(isBomb ? "💣" : ""); // Set symbol
    
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
    
        if (score >= 80) {
            FXGL.runOnce(() -> App.showWinScreen(), Duration.seconds(0.5));
        }
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