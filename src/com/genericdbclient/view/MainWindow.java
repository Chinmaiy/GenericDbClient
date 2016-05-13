/**
 * 
 */
package com.genericdbclient.view;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * @author Cezara C.
 * @author Marian F.
 * @version 1.0
 *
 */
public class MainWindow {
	
	private Logger logger = Logger.getLogger(MainWindow.class.getName());
	
	private Stage stage;
	
	private final int MIN_WIDTH  = 800;
	private final int MIN_HEIGHT = 700;
	
	private final String TITLE = "DatabaseClient";
	private final String MAIN_ICON = "images/dbicon.png";
	
	private DbMetadataTreeView dbMetadataTreeView;
	private TabPaneView tabPaneView;
	
	public MainWindow(Stage stage) {
		
		this.stage = stage;
		
		this.decorateTitleBar();
		this.setMinSize();
		
		/*should not have this kind of stuff here; if there is an exception it should appear an alert message or something*/
		try {
			dbMetadataTreeView = new DbMetadataTreeView(this);
		} catch (SQLException e) {
			
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		
		tabPaneView = new TabPaneView(this);
		
		SplitPane splitPane = new SplitPane(dbMetadataTreeView, tabPaneView);
		
		Scene scene = new Scene(splitPane);
		this.stage.setScene(scene);
	}
	
	public TabPaneView getTabPaneView() {
		
		return tabPaneView;
	}
	
	private void decorateTitleBar() {
		
		stage.setTitle(TITLE);
		stage.getIcons().add(new Image(getClass().getResource(MAIN_ICON).toExternalForm()));
	}
	
	private void setMinSize() {

		stage.setMinWidth(MIN_WIDTH);
		stage.setMinHeight(MIN_HEIGHT);
	}
	
	public Stage getStage() {
		return stage;
	}
}
