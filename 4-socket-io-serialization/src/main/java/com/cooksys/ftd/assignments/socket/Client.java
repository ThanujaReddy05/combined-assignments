package com.cooksys.ftd.assignments.socket;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.cooksys.ftd.assignments.socket.model.Config;
import com.cooksys.ftd.assignments.socket.model.Student;

public class Client {

	/**
	 * The client should load a {@link com.cooksys.ftd.assignments.socket.model.Config} object from the
	 * <project-root>/config/config.xml path, using the "port" and "host" properties of the embedded
	 * {@link com.cooksys.ftd.assignments.socket.model.RemoteConfig} object to create a socket that connects to
	 * a {@link Server} listening on the given host and port.
	 *
	 * The client should expect the server to send a {@link com.cooksys.ftd.assignments.socket.model.Student} object
	 * over the socket as xml, and should unmarshal that object before printing its details to the console.
	 * @throws IOException 
	 * @throws UnknownHostException 
	 * @throws JAXBException 
	 * @throws ParserConfigurationException 
	 * @throws SAXException 
	 * @throws TransformerException 
	 */
	public static void main(String[] args)  {
		String cpath = "config/config.xml";
		Config config = new Config();

		//Load config file 
		//Create socket BufferReader for client server communication
		Socket soc;
		BufferedReader reader;
		try {
			JAXBContext jaxb =  Utils.createJAXBContext();  
			config = Utils.loadConfig(cpath, jaxb);

			//Get host and port from config object
			String  host = config.getRemote().getHost();
			int port = config.getRemote().getPort();

			soc = new Socket(host, port);
			reader = new BufferedReader(new InputStreamReader(soc.getInputStream()));
			String xmlfile = reader.readLine(); // Read the buffer data into a String 
			String xmlFileF = xmlfile.substring(0, xmlfile.lastIndexOf('>')+1); // Truncate the tailing string after xml root element

			//Parse xml string to Document
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document =  builder.parse(new InputSource(new StringReader(xmlFileF)));

			//Transform document to .xml file using DOM
			TransformerFactory tranFactory = TransformerFactory.newInstance();   
			Transformer aTransformer = tranFactory.newTransformer();   
			Source src = new DOMSource( document );   
			Result dest = new StreamResult( new File("config/recievedFile.xml" ) );   
			aTransformer.transform( src, dest );  
			String rpath = "config/recievedFile.xml";

			//Unmarshall and load the student object
			JAXBContext jaxb1 =  Utils.createJAXBContext();  //jaxb object to unmarshal the recieved xml file 
			Student student = Server.loadStudent(rpath, jaxb1);

			//Display student object to the console 
			System.out.println(student);

			reader.close();
			soc.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}




	}
}
