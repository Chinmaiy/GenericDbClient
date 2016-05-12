/**
 * 
 */
package com.genericdbclient.controller;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.genericdbclient.database.DbUtils;
import com.genericdbclient.view.TabView;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellEditEvent;

/**
 * @author Cezara C.
 * @author Marian F.
 * @version 1.0
 *
 */
public class OnEditCommitTableCellHandler implements EventHandler<CellEditEvent<ObservableList<String>, String>> {
	
	private Logger logger = Logger.getLogger(OnEditCommitTableCellHandler.class.getName());
	
	private TabView tabView;
	
	public OnEditCommitTableCellHandler(TabView tabView) {
		
		this.tabView = tabView;
	}

	@Override
	public void handle(CellEditEvent<ObservableList<String>, String> event) {
		
		int colIndex = event.getTablePosition().getColumn();
		int rowIndex = event.getTablePosition().getRow();
		
		TableView<ObservableList<String>> tableView = event.getTableView();
		
		ObservableList<String> row = tableView.getItems().get(rowIndex);
		row.set(colIndex, event.getNewValue());
		
		try {
			DbUtils.updateValue(tabView.getText(),
					tabView.getColumnName(colIndex), 
					event.getNewValue(), 
					tabView.getColumnsNames(), 
					tabView.getRowValues(rowIndex));
			
			tabView.setCellValue(rowIndex, colIndex, event.getNewValue());
			
		} catch (SQLException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		
		
	}

}
