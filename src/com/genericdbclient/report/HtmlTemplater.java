package com.genericdbclient.report;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.xml.sax.SAXException;

/**
 * Command for assigning the favorite songs, given as a list of audio files, to a html/css/js template.
 * @author F. Marian, C. Cezara
 *
 */
public class HtmlTemplater {

	class RowVal{
		public List<String> values;
	}
	
	public HtmlTemplater() {
		
	}
	
	
	/**
	 * Executes the "htmlize" command
	 * @throws ShellException when unable to compute the files meta data (Tika Exception) or unable to merge and write the template to disk (Velocity Exception)  
	 */
	public void execute(List<String> columnNames, List<List<String>> rows) {
		
        VelocityEngine ve = getVelocityEngine();			//call the velocity engine initialiser (loads the template folder)
        Template template = ve.getTemplate("template.vm");	//load the template called 'template.vm' from the template folder
        VelocityContext context = new VelocityContext();	//create a new context for this template
        
        
        context.put("columns", columnNames);					//put songs in the context
        
        
        context.put("rows", rows);
        
       
        mergeTemplateAndWriteToFile(template, context);		//merge the template and write the HTML file do disk
		
	}
	
	
	
	/**
	 * Computes the properties (here, hard coded, but can be later configured in a configuration file) for the Velocity Engine (Mainly, the folder with the templates) 
	 * @return a VelocityEngine instance with the templates folder set
	 */
	private VelocityEngine  getVelocityEngine(){

	    VelocityEngine ve = new VelocityEngine();
	    Properties props = new Properties();
	    
	    String path = Paths.get(".").toAbsolutePath().normalize().toString() + "\\templates";
	    props.put("file.resource.loader.path", path);
	    props.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.NullLogSystem");
		ve.init(props);
		
	    return ve;
	}
	
	
	/**
	 * Merges the template with the given context and then attempts to write the html file
	 * @param template is the template
	 * @param context is the data for the template
	 * @throws FileException if cannot write the file
	 */
	private void mergeTemplateAndWriteToFile(Template template, VelocityContext context)
	{
		//create the fileWriter which writes the file
        FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(Paths.get(".").toAbsolutePath().normalize().toString() + "\\templates\\output.html");
		} catch (IOException e) {
			System.out.println(". Technical details: \n" + e.getMessage());
		}
        
		//merge the template (basically replace the template with the actual data) a.k.a. data binding
		template.merge(context, fileWriter);
		
		//flush (write all to disk)
		try {
			fileWriter.flush();
		} catch (IOException e1) {

			System.out.println(". Technical details: \n" + e1.getMessage());
		}
	}
	
	
	
	
	

}
