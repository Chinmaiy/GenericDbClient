/**
 * 
 */
package com.genericdbclient.view;

import javafx.scene.control.TabPane;

/**
 * @author Cezara C.
 * @author Marian F.
 * @version 1.0
 *
 */
public class TabPaneView extends TabPane {
	
	private MainWindow mainWindow;
	
	public TabPaneView(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
	}
	
	public MainWindow getMainWindow() {
		return mainWindow;
	}

}
