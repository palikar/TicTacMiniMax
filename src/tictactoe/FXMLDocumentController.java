/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 *
 * @author s_stanis
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Canvas canvas;
    GraphicsContext gc;
    Game game;
    int width = 500;
    int heigth = 500;
    boolean running = true;
    int bigWinner = -1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.gc = canvas.getGraphicsContext2D();
        game = new Game();
        game.winning = (Integer winner) -> {
            bigWinner = winner;
            running = false;
        };

        canvas.setOnMouseClicked((MouseEvent me) -> {

            if (me.isAltDown()) {
                game.reset();
                running = true;
                bigWinner = -1;
                renderGame();
                return;
            }

            if (!running) {
                return;
            }
            int xSel = (int) me.getX() / (width / 3);
            int ySel = (int) me.getY() / (heigth / 3);
            game.makeMove(xSel, ySel);
            renderGame();
        });

        renderGame();

    }

    public void renderGame() {
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, width, heigth);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(3);
        for (int i = 1; i < 3; i++) {
            gc.strokeLine(0, i * (heigth / game.rows), 500, i * (heigth / game.rows));
        }
        for (int i = 1; i < 3; i++) {
            gc.strokeLine(i * (heigth / game.cols), 0, i * (heigth / game.cols), 500);
        }
        int[][] gameState = game.getState();
        for (int i = 0; i < game.rows; i++) {
            for (int j = 0; j < game.cols; j++) {
                if (gameState[i][j] == 0) {
                    continue;
                } else if (gameState[i][j] == 1) {
                    drawX(i, j);
                } else if (gameState[i][j] == 2) {
                    drawO(i, j);
                }
            }
        }

        if (bigWinner == 2) {
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(10);
            gc.setFont(new Font(100));
            gc.strokeText("YOU LOSE,", 10, 225);
            gc.strokeText("BITCH!", 50, 325);

            gc.setFont(new Font(100));
            gc.setFill(Color.DARKTURQUOISE);
            gc.fillText("YOU LOSE,", 10, 225);
            gc.fillText("BITCH!", 50, 325);

        }
        if (bigWinner == 0) {
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(10);
            gc.setFont(new Font(100));
            gc.strokeText("GG WP", 75, 225);

            gc.setFill(Color.DARKTURQUOISE);
            gc.setFont(new Font(100));
            gc.fillText("GG WP", 75, 225);

        }

    }

    private void drawX(int row, int col) {
        int x = col * (width / game.cols);
        int y = row * (heigth / game.rows);
        gc.setStroke(Color.RED);
        gc.setLineWidth(15);
        gc.strokeLine(x + 13, y + 13, x + width / game.cols - 25, y + heigth / game.rows - 25);
        gc.strokeLine(x + width / game.cols - 25, y + 13, x + 13, y + heigth / game.rows - 25);
    }

    private void drawO(int row, int col) {
        int x = col * (width / game.cols);
        int y = row * (heigth / game.rows);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(15);
        gc.strokeOval(x + 12, y + 12, width / game.cols - 25, heigth / game.rows - 25);

    }

}
