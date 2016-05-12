/**
 * 
 */
package com.genericdbclient.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.genericdbclient.database.DbUtils;
import com.genericdbclient.view.TabView;

import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * @author Cezara C.
 * @author Marian F.
 * @version 1.0
 *
 */
public class OnInsertButtonClickedHandler implements EventHandler<MouseEvent> {
	
	private Logger logger = Logger.getLogger(OnInsertButtonClickedHandler.class.getName());
	
	private TabView tabView;
	
	public OnInsertButtonClickedHandler(TabView tabView) {
		
		this.tabView = tabView;
	}

	@Override
	public void handle(MouseEvent event) {
		
		/*get and add new values to table view*/
		List<TextField> columnsValuesTextFields = tabView.getColumnsValuesTextFields();
		
		List<String> newRowValues = new ArrayList<String>();
		
		for (TextField columnTextField : columnsValuesTextFields) {
			
			newRowValues.add(columnTextField.getText());
			columnTextField.clear();
		}
		
		tabView.addRowValues(newRowValues);
		
		/*add to database*/
		try {
			DbUtils.insert(tabView.getText(), tabView.getColumnsNames(), newRowValues);
		} catch (SQLException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	
}
