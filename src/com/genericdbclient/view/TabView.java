/**
 * 
 */
package com.genericdbclient.view;

import java.util.ArrayList;
import java.util.List;

import com.genericdbclient.controller.OnDbGraphButtonClickedHandler;
import com.genericdbclient.controller.OnDeleteButtonClickedHandler;
import com.genericdbclient.controller.OnEditCommitTableCellHandler;
import com.genericdbclient.controller.OnEnterFilterTextFieldHandler;
import com.genericdbclient.controller.OnInsertButtonClickedHandler;
import com.genericdbclient.controller.OnReportButtonClickedHandler;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
	
	private CustomButton reportButton;
	private CustomButton dbGraphButton;
	
	private TextField filterTextField;
	private CustomButton filterButton;

	private TableView<ObservableList<String>> tableView;
	
	private List<String> columnsNames;
	private List<List<String>> rowsValues;
	
	private CustomButton insertButton;
	private List<TextField> columnsValuesTextFields;
	
	private CustomButton deleteSelectedButton;
	
	public TabView(String tabName, List<String> columnsNames, List<List<String>> rowsValues) {
		
		reportButton = new CustomButton("Report");
		reportButton.setOnMouseClicked(new OnReportButtonClickedHandler(this));
		
		dbGraphButton = new CustomButton("Graph");
		dbGraphButton.setOnMouseClicked(new OnDbGraphButtonClickedHandler(this));
		
		CustomHBox fancyButtonsBox = new CustomHBox();
		fancyButtonsBox.getChildren().addAll(reportButton, dbGraphButton);

		initFilterTextField();
		
		filterButton = new CustomButton("Filter");
		filterButton.setMinWidth(filterButton.getText().length() * 7 + 20);
		
		CustomHBox filterBox = new CustomHBox();
		filterBox.getChildren().addAll(filterTextField, filterButton);
		
		tableView = new TableView<>();
		tableView.setEditable(true);

		this.columnsNames = columnsNames;
		
		this.addColumns();
		
		this.setRowsValues(rowsValues);
		
		this.setText(tabName);
		
		this.initColumnsValuesTextFields();
		
		insertButton = new CustomButton("Insert");
		insertButton.setMinWidth(insertButton.getText().length() * 7 + 20);
		insertButton.setOnMouseClicked(new OnInsertButtonClickedHandler(this));
		
		CustomHBox insertBox = new CustomHBox();
		insertBox.getChildren().add(insertButton);
		insertBox.getChildren().addAll(columnsValuesTextFields);
		
		deleteSelectedButton = new CustomButton("Delete Selected");
		deleteSelectedButton.setOnMouseClicked(new OnDeleteButtonClickedHandler(this));
		
		CustomVBox bigBox = new CustomVBox();
		bigBox.getChildren().addAll(fancyButtonsBox, filterBox, tableView, insertBox, deleteSelectedButton);
		
		this.setContent(bigBox);
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
	
	public void setRowsValues(List<List<String>> rowsValues) {

		this.rowsValues = rowsValues;
		
		ObservableList<ObservableList<String>> rows = FXCollections.observableArrayList();
		
		for (List<String> rowValues : rowsValues) {
			
			ObservableList<String> row = FXCollections.observableArrayList(rowValues);
			
			rows.add(row);
		}
		
		tableView.setItems(rows);
	}
	
	public void addRowValues(List<String> rowValues) {
		
		ObservableList<String> row = FXCollections.observableArrayList();
		
		for (String string : rowValues) {
			
			if(string == null || string.contentEquals(""))
				row.add("null");
			else
				row.add(string);
		}
		
		tableView.getItems().add(row);
		
		this.rowsValues.add(rowValues);
	}
	
	public void removeRowValues(int rowIndex) {
		
		tableView.getItems().remove(rowIndex);
		
		this.rowsValues.remove(rowIndex);
	}
	
	private void initFilterTextField() {
		
		filterTextField = new TextField();
		String filterPromptText = "Filter options - e.g. column_name > value AND ...";
		filterTextField.setPromptText(filterPromptText);
		filterTextField.setPrefWidth(filterPromptText.length() * 7);
		
		filterTextField.setOnKeyPressed(new OnEnterFilterTextFieldHandler(this));
	}
	
	private void initColumnsValuesTextFields() {
		
		columnsValuesTextFields = new ArrayList<TextField>();
		for (String columnName : columnsNames) {
			
			TextField textField = new TextField();
			textField.setId(columnName);
			textField.setPromptText(columnName);
			
			columnsValuesTextFields.add(textField);
		}
	}
	
	/*some getters and setters*/
	public void setTableView(TableView<ObservableList<String>> tableView) {
		this.tableView = tableView;
	}
	
	public TableView<ObservableList<String>> getTableView() {
		return this.tableView;
	}
	
	public List<String> getColumnsNames() {
		return columnsNames;
	}
	
	public String getColumnName(int columnIndex) {
		return columnsNames.get(columnIndex);
	}
	
	public List<List<String>> getRowsValues() {
		
		return rowsValues;
	}
	
	public List<String> getRowValues(int rowIndex) {
		
		return rowsValues.get(rowIndex);
	}
	
	public void setCellValue(int row, int col, String value) {
		
		rowsValues.get(row).set(col, value);
	}
	
	public List<TextField> getColumnsValuesTextFields() {
		return this.columnsValuesTextFields;
	}
	
	public TextField getFilterTextField() {
		return filterTextField;
	}
}
