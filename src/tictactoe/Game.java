/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;

/**
 *
 * @author s_stanis
 */
public class Game {

    int cols = 3, rows = 3;
    int[][] gameState;
    Consumer<Integer> winning;
    boolean end = false;

    public Game() {
        gameState = new int[rows][cols];
        reset();

    }

    public void reset() {
        this.end = false;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                gameState[i][j] = 0;
            }
        }
    }

    public int[][] getState() {
        return gameState;
    }

    void makeMove(int xSel, int ySel) {
        if (gameState[ySel][xSel] != 0 || end) {
            return;
        }
        gameState[ySel][xSel] = 1;

        if (getWinner(gameState) == 1) {
            winning.accept(1);
            return;
        }
        if (getWinner(gameState) == 0 && isBoardFull(gameState)) {
            winning.accept(0);
            return;
        }

        gameState = getNextMove(gameState);

        if (isBoardFull(gameState) && getWinner(gameState) == 0) {
            winning.accept(0);
            return;
        }

        if (getWinner(gameState) == 2) {
            winning.accept(2);

        }

    }

    private int[][] getNextMove(int[][] currentState) {
        ArrayList<int[][]> nextStates = getNextStates(currentState, 2);
        int bestSoFar = Integer.MIN_VALUE;
        int index = 0;
        for (int i = 0; i < nextStates.size(); i++) {
            int stateValue = miniMax(nextStates.get(i), 1, true);
            if (stateValue > bestSoFar) {
                bestSoFar = stateValue;
                index = i;
            }
        }

        return nextStates.get(index);
    }

    private ArrayList<int[][]> getNextStates(int state[][], int player) {
        ArrayList<int[][]> nextStates = new ArrayList<>();
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[0].length; j++) {
                if (state[i][j] == 0) {
                    int newState[][];
                    newState = Arrays.stream(state)
                            .map((int[] row) -> row.clone())
                            .toArray((int length) -> new int[length][]);
                    newState[i][j] = player;
                    nextStates.add(newState);
                }
            }
        }
        return nextStates;
    }

    private int miniMax(int state[][], int depth, boolean minimazing) {
        int winner = getWinner(state);

        if (winner == 2) {
            return Integer.MAX_VALUE - depth;
        }
        if (winner == 1) {
            return depth + Integer.MIN_VALUE;
        }
        if (isBoardFull(state)) {
            return 0;
        }

        if (minimazing) {
            ArrayList<int[][]> moves = getNextStates(state, 1);
            int bestSoFar = Integer.MAX_VALUE;
            for (int[][] nextMove : moves) {
                final int val = miniMax(nextMove, depth + 1, false);
                bestSoFar = Math.min(val, bestSoFar);
            }
            return bestSoFar;
        } else {
            ArrayList<int[][]> moves = getNextStates(state, 2);
            int bestSoFar = Integer.MIN_VALUE;
            for (int[][] nextMove : moves) {
                final int val = miniMax(nextMove, depth + 1, true);

                bestSoFar = Math.max(bestSoFar, val);
            }
            return bestSoFar;
        }
    }

    private boolean isBoardFull(int board[][]) {

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public int getWinner(int board[][]) {
        int winner;

        if (board[0][0] != 0) {
            if (board[0][1] == (board[0][0]) && board[0][2] == (board[0][0])) {
                winner = board[0][0];
                return winner;
            }
            if (board[1][0] == (board[0][0]) && board[2][0] == (board[0][0])) {
                winner = board[0][0];
                return winner;
            }
        }

        if (board[1][1] != 0) {
            if (board[0][0] == (board[1][1]) && board[2][2] == (board[1][1])) {
                winner = board[1][1];
                return winner;
            }
            if (board[2][0] == (board[1][1]) && board[0][2] == (board[1][1])) {
                winner = board[1][1];
                return winner;
            }
            if (board[1][0] == (board[1][1]) && board[1][2] == (board[1][1])) {
                winner = board[1][1];
                return winner;
            }

            if (board[0][1] == (board[1][1]) && board[2][1] == (board[1][1])) {
                winner = board[1][1];
                return winner;
            }
        }

        if (board[2][2] != (0)) {
            if (board[0][2] == (board[2][2]) && board[1][2] == (board[2][2])) {
                winner = board[2][2];
                return winner;
            }
            if (board[2][0] == (board[2][2]) && board[2][1] == (board[2][2])) {
                winner = board[2][2];
                return winner;
            }
        }
        return 0;
    }

    private int getValue(int winner, int depth) {

        if (winner == 1) {
            return Integer.MIN_VALUE + depth;
        } else if (winner == 2) {
            return Integer.MAX_VALUE - depth;
        } else {
            return 0;
        }
    }

    private void displayState(int state[][]) {
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[0].length; j++) {
                System.out.print(state[i][j] + "|");
            }
            System.out.println();
        }
        System.out.println("--------------------");
    }

}
