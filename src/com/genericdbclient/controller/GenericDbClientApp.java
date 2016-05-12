/**
 * 
 */
package com.genericdbclient.controller;

import java.util.ArrayList;
import java.util.List;

import com.genericdbclient.database.DbUtils;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * @author Cezara C.
 * @author Marian F.
 * @version 1.0
 *
 */
public class GenericDbClientApp extends Application {

	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		MainController mainController = new MainController();
		
		mainController.initMainWindow(primaryStage);
		
		primaryStage.show();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Application.launch(args);
	}

}
