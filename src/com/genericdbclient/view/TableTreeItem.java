/**
 * 
 */
package com.genericdbclient.view;

import java.util.List;

import javafx.scene.control.TreeItem;

/**
 * @author Cezara C.
 * @author Marian F.
 * @version 1.0
 *
 */
public class TableTreeItem extends TreeItem<String> {
	
	private List<String> columnNames;
	
	public TableTreeItem(String tableName, List<String> columnNames) {
		
		super(tableName);
		
		this.columnNames = columnNames;
		
		for (String column : columnNames) {
			TreeItem<String> columnNameItem = new TreeItem<String>(column);
			this.getChildren().add(columnNameItem);
		}
	}
	
	public List<String> getColumnNames() {
		return columnNames;
	}
}
