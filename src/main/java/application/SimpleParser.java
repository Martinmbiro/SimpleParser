package application;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.json.JSONObject;
import org.json.XML;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 * A simple class that parses and processes a specific kind of XML into a JSON
 * object and then writes on to a file.
 *
 * @author [add your name here]
 */
public class SimpleParser {
    // Add whatever attributes you think you'll need

    /**
     * Constructor for a SimpleParser
     */
    public SimpleParser() {
        // If appropriate/needed, set up your SimpleParser object here
    }

    /**
     *
     * @param args The program (command line) argument list
     *             args[0] = the XML file (path to it)
     *             args[1] = the JSON file (path to it) that will be created
     *             Describe any other arguments you have implemented for.
     */
    public static void main(String[] args) {
    	//A new Object of SimpleParser (this) class
    	SimpleParser parser = new SimpleParser();
    	
    	//File path to the XML file (within the current project)
    	String xmlFilePath = "src/test/resources/2019Dec.xml";
    	
    	//Destination path to the JSON file to be created (within the current project)
    	String jsonFileDestination = "src/test/resources/sample.json";
    	
    	parser.saveJSON(parser.parseXMLtoJSON(xmlFilePath), jsonFileDestination);
    }

    /**
     * This method parses and processes information from an XML file
     * into a JSON object as per the description in the assignment document.
     *
     * @param filePath The absolute or relative path to the XML file to be parsed
     * @return Returns String that contains a JSON object that represents
     *         the relevant data from the XML file
     */
    public String parseXMLtoJSON(String filePath){    	
        String jsonOut = "";
        
        try {
			DocumentBuilderFactory docBuilder = DocumentBuilderFactory.newInstance();
			InputSource path = new InputSource(filePath);
			
			Document document = docBuilder.newDocumentBuilder().parse(path);
			StringWriter stringWriter = new StringWriter();
			
			
			Transformer serializer = TransformerFactory.newInstance().newTransformer();
			serializer.transform(new DOMSource(document), new StreamResult(stringWriter));
			
			jsonOut = stringWriter.toString();
			
		} catch (Exception e) {
			// Handle exception
			System.out.println(e.getMessage());
		}
        return jsonOut;
    }

    /**
     * Writes a string containing a JSON object into a file
     * @param jsonObj The string containing the JSON object
     * @param filePath The absolute or relative path to the JSON file to be written
     */
    public void saveJSON(String jsonObj, String filePath){
        // Make a JSON Object from the XML String
    	JSONObject jsonObject = XML.toJSONObject(jsonObj);
    	
    	try {
    		//Check for anomalies in parseXMLtoJSON(String filePath) method call
        	if(jsonObj.equals("")) {
        		throw new Exception("SOMETHING WENT WRONG");
        	}
    		
    		//Create a FileWriter object for a JSON file to the written to the passed filePath
			FileWriter writer = new FileWriter(filePath);
			writer.write(jsonObject.toString());
			writer.close();
			
			//Let the user know that the JSON file has been successfully created
			System.out.println("JSON file created successfully at: \""+filePath+"\"");
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    }
}
