/**
 * 
 */
package com.genericdbclient.report;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.genericdbclient.view.TabView;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.exception.DRException;

/**
 * @author Cezara C.
 * @author Marian F.
 * @version 1.0
 *
 */
public class Report {
	
	private Logger logger = Logger.getLogger(Report.class.getName());

	private TabView tabView;
	
	private JasperReportBuilder report;
	
	public Report(TabView tabView) {
		this.tabView = tabView;
	}
	
	public void build() {
		
		this.report = DynamicReports.report();
		
		List<String> columns = tabView.getColumnsNames();
		
		for (String column : columns) {
			report.addColumn(DynamicReports.col.column(column, column, DataTypes.stringType()));
		}
		
		report.addTitle(Components.text(tabView.getText()));
		
		//report.setDataSource(FXCollections.observableArrayList());
	}
	
	public void write(File file) {
		try {
            this.report.toPdf(new FileOutputStream(file.getAbsolutePath() + ".pdf"));
            Desktop desktop = Desktop.getDesktop();
            desktop.open(file);
        } catch (DRException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        } catch (FileNotFoundException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        } catch (IOException e) {
        	logger.log(Level.SEVERE, e.getMessage(), e);		}
    }
}
