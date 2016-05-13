/**
 * 
 */
package com.genericdbclient.view;

import java.sql.SQLException;
import java.util.List;

import com.genericdbclient.controller.OnTableNameClickedListener;
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
	
	@SuppressWarnings("unused")
	private MainWindow mainWindow;
	
	/*these could be taken from a configuration file*/
	private final String[] objectTypes = { "table", "view", "function", "procedure", "trigger"};
	
	public DbMetadataTreeView(MainWindow mainWindow) throws SQLException {
		
		this.mainWindow = mainWindow;
		
		this.setMaxWidth(200);
		this.setPrefWidth(200);
		this.setMinWidth(200);
		
		TreeItem<String> rootItem = new TreeItem<String>("Database Metadata");
		rootItem.setExpanded(true);
		
		for(int indx = 0; indx < objectTypes.length; ++indx) {
			
			TreeItem<String> objectTypeItem = new TreeItem<String>(objectTypes[indx]);
			rootItem.getChildren().add(objectTypeItem);
			
			/*should decouple this somehow; as of now the view is calling the database directly?*/
			List<String> objectNames = DbUtils.getObjectNames(objectTypes[indx]);
			
			for (String objectName : objectNames) {
				
				if(objectTypes[indx].contentEquals("table")) {
					
					List<String> columnNames = DbUtils.getTableColumns(objectName);
					
					TableTreeItem tableNameItem = new TableTreeItem(objectName, columnNames);
					
					objectTypeItem.getChildren().add(tableNameItem);
				}
				else {
					TreeItem<String> objectNameItem = new TreeItem<String>(objectName);
					objectTypeItem.getChildren().add(objectNameItem);
				}
			}
		}
		
		this.setRoot(rootItem);
		
		this.getSelectionModel().selectedItemProperty().addListener(new OnTableNameClickedListener(mainWindow));
	}
}
