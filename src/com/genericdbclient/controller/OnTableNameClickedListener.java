/**
 * 
 */
package com.genericdbclient.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.genericdbclient.database.DbUtils;
import com.genericdbclient.view.MainWindow;
import com.genericdbclient.view.TabView;
import com.genericdbclient.view.TableTreeItem;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeItem;

/**
 * @author Cezara C.
 * @author Marian F.
 * @version 1.0
 *
 */
public class OnTableNameClickedListener implements ChangeListener<TreeItem<String>> {
	
	private static final Logger logger = Logger.getLogger(OnTableNameClickedListener.class.getName());
	
	private MainWindow mainWindow;
	
	public OnTableNameClickedListener(MainWindow mainWindow) {
		
		this.mainWindow = mainWindow;
	}
	
	@Override
	public void changed(ObservableValue<? extends TreeItem<String>> observable, TreeItem<String> oldValue,
			TreeItem<String> newValue) {
		
		TreeItem<String> treeItem = newValue;
		
		if(treeItem.getParent() == null)
			logger.log(Level.INFO, "Selected root.");
		else {
			if(treeItem instanceof TableTreeItem) {
				
				try {
					List<List<String>> data = DbUtils.getAll(treeItem.getValue());
					
					List<String> columns = ((TableTreeItem) treeItem).getColumnNames();
					
					TabView tabView = new TabView(treeItem.getValue(), columns, data);
					
					mainWindow.getTabPaneView().getTabs().add(tabView);
					
				} catch (SQLException e) {
					
					logger.log(Level.SEVERE, e.getMessage(), e);
				}
			}
			else
				logger.log(Level.INFO, "Selected something else.");
		}
	}

}
