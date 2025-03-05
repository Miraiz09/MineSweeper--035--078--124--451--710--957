package com.project;

    import java.util.Scanner;
import java.io.InputStream;

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

    public Minesweeper(String fieldFile) {
        this.fieldFileName = fieldFile;
        initFromFile(fieldFileName);
    }

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

    void setMineCell(int x, int y) {
        cells[x][y] = IS_MINE;
    }

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

    void revealCell(int row, int col) {
        if (revealed[row][col]) {
            System.out.println("Cell already revealed!");
            return;
        }
        revealed[row][col] = true;

        if (cells[row][col] == IS_MINE) {
            System.out.println("Boom! You hit a mine!");
        }
    }
    
}