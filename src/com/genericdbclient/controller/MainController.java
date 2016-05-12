/**
 * 
 */
package com.genericdbclient.controller;

import com.genericdbclient.view.MainWindow;

import javafx.stage.Stage;

/**
 * @author Cezara C.
 * @author Marian F.
 * @version 1.0
 *
 */
public class MainController {
	
	private MainWindow mainWindow;

	public MainController() {}
	
	public void initMainWindow(Stage primaryStage) {
		
		this.mainWindow = new MainWindow(primaryStage);
	}
}
