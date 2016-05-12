/**
 * 
 */
package com.genericdbclient.view;

import java.sql.SQLException;
import java.util.List;

import com.genericdbclient.database.DbUtils;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

/**
 * @author Cezara C.
 * @author Marian F.
 * @version 1.0
 *
 */
public class DbMetadataTreeView extends TreeView<String> {
	
	private final String[] objectTypes = { "table", "view", "function", "procedure", "trigger"};
	
	public DbMetadataTreeView() throws SQLException {
		
		TreeItem<String> rootItem = new TreeItem<String>("Database Metadata");
		rootItem.setExpanded(true);
		
		for(int indx = 0; indx < objectTypes.length; ++indx) {
			
			TreeItem<String> objectTypeItem = new TreeItem<String>(objectTypes[indx]);
			rootItem.getChildren().add(objectTypeItem);
			
			/*should decouple this somehow; as of now the view is calling the database directly?*/
			List<String> objectNames = DbUtils.getObjectNames(objectTypes[indx]);
			
			for (String name : objectNames) {
				TreeItem<String> objectNameItem = new TreeItem<String>(name);
				objectTypeItem.getChildren().add(objectNameItem);
				
				if(objectTypes[indx].contentEquals("table")) {
					List<String> columnNames = DbUtils.getTableColumns(name);
					
					for (String column : columnNames) {
						TreeItem<String> columnNameItem = new TreeItem<String>(column);
						objectNameItem.getChildren().add(columnNameItem);
					}
				}
			}
		}
		
		this.setRoot(rootItem);
	}
}
