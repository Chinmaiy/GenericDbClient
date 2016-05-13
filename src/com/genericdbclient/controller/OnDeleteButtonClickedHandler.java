/**
 * 
 */
package com.genericdbclient.controller;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.genericdbclient.database.DbUtils;
import com.genericdbclient.view.TabView;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * @author Cezara C.
 * @author Marian F.
 * @version 1.0
 *
 */
public class OnDeleteButtonClickedHandler implements EventHandler<MouseEvent> {
	
	private Logger logger = Logger.getLogger(OnDeleteButtonClickedHandler.class.getName());
	
	private TabView tabView;
	
	public OnDeleteButtonClickedHandler(TabView tabView) {
		this.tabView = tabView;
	}

	@Override
	public void handle(MouseEvent event) {
		
		int rowIndex = tabView.getTableView().getSelectionModel().getSelectedIndex();
		
		/*there is actually a row selected*/
		if(rowIndex >= 0) {
			
			try {
				DbUtils.delete(tabView.getText(), tabView.getColumnsNames(), tabView.getRowValues(rowIndex));
				tabView.removeRowValues(rowIndex);
			} catch (SQLException e) {
				
				logger.log(Level.SEVERE, e.getMessage(), e);
			}
		}
		
		
		/*else an alert?*/
	}

}
