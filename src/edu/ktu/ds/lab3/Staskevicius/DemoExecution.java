/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ktu.ds.lab3.Staskevicius;

import edu.ktu.ds.lab3.Manogui.MainWindow;
import java.util.Locale;
import javafx.application.Application;
import static javafx.application.Application.STYLESHEET_CASPIAN;
import static javafx.application.Application.setUserAgentStylesheet;
import javafx.stage.Stage;

/**
 *
 * @author Tomas
 */
public class DemoExecution extends Application {
     public static void main(String[] args) {
        edu.ktu.ds.lab3.demo.DemoExecution.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Locale.setDefault(Locale.US); // Suvienodiname skaičių formatus 
        ManualTest.executeTest();
        //setUserAgentStylesheet(STYLESHEET_MODENA);    //Nauja FX išvaizda
        setUserAgentStylesheet(STYLESHEET_CASPIAN); //Sena FX išvaizda
        MainWindow.createAndShowGui(primaryStage);
    }
}
