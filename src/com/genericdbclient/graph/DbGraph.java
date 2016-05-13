/**
 * 
 */
package com.genericdbclient.graph;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import com.genericdbclient.database.DbUtils;

/**
 * @author Cezara C.
 * @author Marian F.
 * @version 1.0
 *
 */
public class DbGraph {
	
	private Logger logger = Logger.getLogger(DbGraph.class.getName());

	public DbGraph() throws SQLException {
		
		Graph graph = new SingleGraph("Database Graph");
		graph.setStrict(false);
		graph.setAutoCreate(true);
		graph.display();
		
		List<String> tables = DbUtils.getObjectNames("table");
		
		for (String table : tables) {
			List<String> referencedTables = DbUtils.getReferencedTables(table);
			
			for (String referencedTable : referencedTables) {
				//logger.log(Level.INFO, table + " -> " + referencedTable);
				
				graph.addNode(table);
				graph.addNode(referencedTable);
				graph.addEdge(referencedTable + "-" + table, referencedTable, table, true);
				
				Node tableNode = graph.getNode(table);
				tableNode.setAttribute("ui.label", table);
				
				tableNode = graph.getNode(referencedTable);
				tableNode.setAttribute("ui.label", referencedTable);
			}
		}
		
		graph.addAttribute("ui.quality");
		graph.addAttribute("ui.antialias");
		graph.addAttribute("ui.stylesheet", "edge { fill-color: blue; }");
	}
}



