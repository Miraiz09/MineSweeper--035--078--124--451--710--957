package com.project;

import java.io.InputStream;
import java.util.Scanner;

public class Minesweeper {
    static char SAFE_CELL = '.';
    static char MINE_CELL = 'X';
    static char HIDDEN_CELL = '*';
    static int IS_SAFE = 0;
    static int IS_MINE = 1;

    int fieldX, fieldY;
    int[][] cells;
    boolean[][] revealed;
    String fieldFileName;

    // Constructor to initialize from file
    public Minesweeper(String fieldFile) {
        this.fieldFileName = fieldFile;
        initFromFile(fieldFileName);
    }

    // Constructor to initialize an empty minefield of given size
    public Minesweeper(int fieldX, int fieldY) {
        this.fieldX = fieldX;
        this.fieldY = fieldY;
        this.cells = new int[fieldX][fieldY];
        this.revealed = new boolean[fieldX][fieldY];
        for (int i = 0; i < fieldX; i++) {
            for (int j = 0; j < fieldY; j++) {
                cells[i][j] = IS_SAFE;
                revealed[i][j] = false;
            }
        }
    }

    // Display the current field (with hidden cells shown as '*')
    void displayField() {
        for (int i = 0; i < fieldX; i++) {
            for (int j = 0; j < fieldY; j++) {
                if (!revealed[i][j]) {
                    System.out.print(HIDDEN_CELL + " ");
                } else {
                    if (cells[i][j] == IS_SAFE) {
                        System.out.print(SAFE_CELL + " ");
                    } else {
                        System.out.print(MINE_CELL + " ");
                    }
                }
            }
            System.out.println();
        }
    }

    // Set a mine at specific coordinates
    void setMineCell(int x, int y) {
        cells[x][y] = IS_MINE;
    }

    // Initialize the minefield from a file
    void initFromFile(String mineFieldFile) {
        InputStream is = getClass().getClassLoader().getResourceAsStream(mineFieldFile);
        if (is == null) {
            System.out.println("File not found: " + mineFieldFile);
            return;
        }

        Scanner scanner = new Scanner(is);
        fieldX = scanner.nextInt();
        fieldY = scanner.nextInt();
        scanner.nextLine();

        cells = new int[fieldX][fieldY];

        for (int i = 0; i < fieldX; i++) {
            String line = scanner.nextLine();
            for (int j = 0; j < fieldY; j++) {
                cells[i][j] = (line.charAt(j) == MINE_CELL) ? IS_MINE : IS_SAFE;
            }
        }
    }

    // Set random mines in the field
    void setRandomMines(int numberOfMines) {
        int minesPlaced = 0;
        while (minesPlaced < numberOfMines) {
            int x = (int) (Math.random() * fieldX);
            int y = (int) (Math.random() * fieldY);
            if (cells[x][y] == IS_SAFE) {
                cells[x][y] = IS_MINE;
                minesPlaced++;
            }
        }
    }

    // Return the type of cell (safe or mine)
    public int getCell(int x, int y) {
        return cells[x][y];
    }
}
