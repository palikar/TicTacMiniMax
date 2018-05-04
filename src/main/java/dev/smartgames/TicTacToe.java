/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.smartgames;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/** @author s_stanis */
public class TicTacToe extends Application {

  @Override
  public void start(Stage stage) throws Exception {
    System.out.println("text");

    Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("FXMLDocument.fxml"));

    Scene scene = new Scene(root);

    stage.setTitle("Advanced tic tac toe");
    stage.setScene(scene);
    stage.show();
  }

  /** @param args the command line arguments */
  public static void main(String[] args) {
    launch(args);
  }
}
