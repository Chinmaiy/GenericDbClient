/**
 * 
 */
package com.genericdbclient.controller;

import java.io.File;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.genericdbclient.report.HtmlTemplater;
import com.genericdbclient.report.Report;
import com.genericdbclient.view.TabPaneView;
import com.genericdbclient.view.TabView;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import net.sf.dynamicreports.report.exception.DRException;

/**
 * @author Cezara C.
 * @author Marian F.
 * @version 1.0
 *
 */
public class OnReportButtonClickedHandler implements EventHandler<MouseEvent> {
	
	private Logger logger = Logger.getLogger(OnReportButtonClickedHandler.class.getName());
	
	private TabView tabView;
	
	public OnReportButtonClickedHandler(TabView tabView) {
	
		this.tabView = tabView;
		
	}

	@Override
	public void handle(MouseEvent event) {
		
		
		Report report = new Report(tabView);
		
		try {
			report.build();
		} catch (SQLException | DRException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		
		FileChooser fileChooser = new FileChooser();
		configureFileChooser(fileChooser);
		
		File file = fileChooser.showSaveDialog(((TabPaneView)tabView.getTabPane()).getMainWindow().getStage());
		
		report.write(file);
		
		/*HtmlTemplater htmlTemplater = new HtmlTemplater();
		htmlTemplater.execute(tabView.getColumnsNames(), tabView.getRowsValues());*/
		
	}
	
	private void configureFileChooser(FileChooser fileChooser) {
		fileChooser.setTitle("Save Report");
		fileChooser.setInitialDirectory(new File(Paths.get(".").toAbsolutePath().normalize().toString()));
		fileChooser.setInitialFileName("untitled");
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
	}
}
