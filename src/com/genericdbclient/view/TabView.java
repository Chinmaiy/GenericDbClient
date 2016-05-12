/**
 * 
 */
package com.genericdbclient.view;

import java.util.List;

import com.genericdbclient.controller.OnEditCommitTableCellHandler;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

/**
 * @author Cezara C.
 * @author Marian F.
 * @version 1.0
 *
 */
public class TabView extends Tab {

	private TableView<ObservableList<String>> tableView;
	
	private List<String> columnsNames;
	private List<List<String>> rowsValues;
	
	public TabView(String tabName, List<String> columnsNames, List<List<String>> rowsValues) {
		
		tableView = new TableView<>();
		tableView.setEditable(true);

		this.columnsNames = columnsNames;
		this.rowsValues = rowsValues;
		
		this.addColumns();
		
		this.addRowsValues();
		
		this.setText(tabName);
		
		this.setContent(tableView);
	}
	
	private void addColumns() {
		
		int columnCount = columnsNames.size();
		
		for (int columnIndx = 0; columnIndx < columnCount; ++columnIndx) {
			
			final int col = columnIndx;
			
			TableColumn<ObservableList<String>, String> column = 
					new TableColumn<ObservableList<String>, String>(columnsNames.get(columnIndx));
			
			column.setCellValueFactory(
					new Callback<TableColumn.CellDataFeatures<ObservableList<String>,String>, ObservableValue<String>>() {
				
				@Override
				public ObservableValue<String> call(CellDataFeatures<ObservableList<String>, String> param) {
			
					return new SimpleStringProperty(param.getValue().get(col).toString());
				}
			});
			
			column.setCellFactory(TextFieldTableCell.forTableColumn());
			column.setOnEditCommit(new OnEditCommitTableCellHandler(this));
			
			tableView.getColumns().add(column);
		}
	}
	
	private void addRowsValues() {
		
		ObservableList<ObservableList<String>> rows = FXCollections.observableArrayList();
		
		for (List<String> rowValues : rowsValues) {
			
			ObservableList<String> row = FXCollections.observableArrayList(rowValues);
			
			rows.add(row);
		}
		
		tableView.setItems(rows);
	}
	
	public List<String> getColumnsNames() {
		return columnsNames;
	}
	
	public String getColumnName(int columnIndx) {
		return columnsNames.get(columnIndx);
	}
	
	public List<String> getRowValues(int rowIndx) {
		
		return rowsValues.get(rowIndx);
	}
	
	public void setCellValue(int row, int col, String value) {
		
		rowsValues.get(row).set(col, value);
	}
}