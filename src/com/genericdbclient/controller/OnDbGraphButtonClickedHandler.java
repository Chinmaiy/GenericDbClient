/**
 * 
 */
package com.genericdbclient.controller;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.genericdbclient.graph.DbGraph;
import com.genericdbclient.view.TabView;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * @author Cezara C.
 * @author Marian F.
 * @version 1.0
 *
 */
public class OnDbGraphButtonClickedHandler implements EventHandler<MouseEvent> {
	
	private Logger logger = Logger.getLogger(OnDbGraphButtonClickedHandler.class.getName());
	
	private TabView tabView;
	
	public OnDbGraphButtonClickedHandler(TabView tabView) {
		this.tabView = tabView;
	}

	@Override
	public void handle(MouseEvent event) {
		
		try {
			DbGraph dbGraph = new DbGraph();
		} catch (SQLException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

}
