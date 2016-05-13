/**
 * 
 */
package com.genericdbclient.controller;

import java.io.File;
import java.nio.file.Paths;

import com.genericdbclient.report.Report;
import com.genericdbclient.view.TabPaneView;
import com.genericdbclient.view.TabView;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

/**
 * @author Cezara C.
 * @author Marian F.
 * @version 1.0
 *
 */
public class OnReportButtonClickedHandler implements EventHandler<MouseEvent> {
	
	private TabView tabView;
	
	public OnReportButtonClickedHandler(TabView tabView) {
	
		this.tabView = tabView;
		
	}

	@Override
	public void handle(MouseEvent event) {
		
		
		Report report = new Report(tabView);
		
		report.build();
		
		FileChooser fileChooser = new FileChooser();
		configureFileChooser(fileChooser);
		
		File file = fileChooser.showSaveDialog(((TabPaneView)tabView.getTabPane()).getMainWindow().getStage());
		
		String fileName = file.getName();
		
		report.write(file);
		
	}
	
	private void configureFileChooser(FileChooser fileChooser) {
		fileChooser.setTitle("Save Report");
		fileChooser.setInitialDirectory(new File(Paths.get(".").toAbsolutePath().normalize().toString()));
		fileChooser.setInitialFileName("untitled");
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
	}
}
