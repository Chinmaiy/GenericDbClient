/**
 * 
 */
package com.genericdbclient.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.genericdbclient.database.DbUtils;
import com.genericdbclient.view.TabView;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * @author Cezara C.
 * @author Marian F.
 * @version 1.0
 *
 */
public class OnEnterFilterTextFieldHandler implements EventHandler<KeyEvent> {
	
	private Logger logger = Logger.getLogger(OnEnterFilterTextFieldHandler.class.getName());
	
	private TabView tabView;
	
	public OnEnterFilterTextFieldHandler(TabView tabView) {
		
		this.tabView = tabView;
	}

	@Override
	public void handle(KeyEvent event) {
		
		if(event.getCode() == KeyCode.ENTER) {
			logger.log(Level.INFO, "Entered on filter text field.");
			
			try {
				List<List<String>> data = DbUtils.getBy(tabView.getText(), tabView.getFilterTextField().getText());
				
				TableView<ObservableList<String>> tableView = tabView.getTableView();
				
				tableView.getItems().clear();
				
				tabView.addRowsValues(data);
				
			} catch (SQLException e) {
				
				logger.log(Level.SEVERE, e.getMessage(), e);
			}
		}
		
		else if(event.getCode() == KeyCode.BACK_SPACE) {
			
			/*for emptyness you should check when the key is release ? */
			/*when remains no filter refresh with get all*/
			if(tabView.getFilterTextField().getText().length() == 1) {
				
				try {
					List<List<String>> data = DbUtils.getAll(tabView.getText());
					
					TableView<ObservableList<String>> tableView = tabView.getTableView();
					
					tableView.getItems().clear();
					
					tabView.addRowsValues(data);
					
				} catch (SQLException e) {
					logger.log(Level.SEVERE, e.getMessage(), e);
				}
				
			}
		}
	}

}
