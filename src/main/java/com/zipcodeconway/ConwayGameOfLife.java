package com.zipcodeconway;

import java.util.Arrays;

public class ConwayGameOfLife {
    int[][] board;
    Integer dimension;

    public ConwayGameOfLife(Integer dimension) {
        this.dimension = dimension;
        board = createRandomStart(dimension);
     }

    public ConwayGameOfLife(Integer dimension, int[][] startmatrix) {
        this.dimension = dimension;
        board = startmatrix;
    }

    public static void main(String[] args) {
        ConwayGameOfLife sim = new ConwayGameOfLife(50);
        int[][] endingWorld = sim.simulate(50);
    }

    // Contains the logic for the starting scenario.
    // Which cells are alive or dead in generation 0.
    // allocates and returns the starting matrix of size 'dimension'
    private int[][] createRandomStart(Integer dimension) {
        int[][] board = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                board[i][j] = (int) Math.round(Math.random());
            }
        }
        return board;
    }

    public int[][] simulate(Integer maxGenerations) {
        int[][] next = new int[board.length][board.length];
        for (int i = 0; i <=maxGenerations; i++) {
            for (int j = 0; j < next[0].length; j++) {
                for (int k = 0; k < next.length; k++) {
                    if(isAlive(k, j, board)){
                        next[k][j] = 1;
                    } else {
                        next[k][j] = 0;
                    }
                }
            }
            copyAndZeroOut(next, board);
            //print(board);
            //break; //these were here so I could debug more easily
        }
        return board;
    }

    // copy the values of 'next' matrix to 'current' matrix,
    // and then zero out the contents of 'next' matrix
    public void copyAndZeroOut(int [][] next, int[][] current) {
        for (int i = 0; i < next[0].length; i++) {
            for (int j = 0; j < next.length; j++) {
                current[i][j] = next[i][j];
                next[i][j] = 0;
            }
        }
    }

    public void print(int[][] board){
        for(int[] arr: board){
            System.out.println(Arrays.toString(arr));
        }
        System.out.println();
    }

    // Calculate if an individual cell should be alive in the next generation.
    // Based on the game logic:
	/*
		Any live cell with fewer than two live neighbours dies, as if by needs caused by underpopulation.
		Any live cell with more than three live neighbours dies, as if by overcrowding.
		Any live cell with two or three live neighbours lives, unchanged, to the next generation.
		Any dead cell with exactly three live neighbours cells will come to life.
	*/
    private boolean isAlive(int row, int col, int[][] world) { //I cant tell if this is just annoying to check or if I
        int neighbors = 0;                                     //did it a dumb way
        //check corners
        if(col != 0 && row != 0){
            neighbors += world[row -1][col-1];
        }
        if(col != 0 && row != board.length-1){
            neighbors += world[row + 1][col - 1];
        }
        if(col != board.length -1 && row != 0){
            neighbors += world[row - 1][col + 1];
        }
        if(col != board.length -1 && row != board.length-1){
            neighbors += world[row + 1][col + 1];
        }
        //check above and below
        if(row == 0){
            neighbors += world[row+1][col];
        } else if (row == board.length - 1){
            neighbors += world[row-1][col];
        } else {
            neighbors += world[row-1][col] + world[row+1][col];
        }
        //check left and right
        if(col == 0){
            neighbors+= world[row][col+1];
        } else if (col == board.length -1 ){
            neighbors += world[row][col-1];
        } else {
            neighbors += world[row][col-1] + world[row][col+1];
        }
        //System.out.printf("[%d][%d]: %d : %d\n", row, col, world[row][col], neighbors);
        //resolve
        if(world[row][col] == 0){
            if(neighbors == 3){
                return true;
            }
            return false;
        } if(neighbors < 2){
            return false;
        } else if(neighbors > 3){
            return false;
        }
        return true;
    }
}
