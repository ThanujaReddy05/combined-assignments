package com.cooksys.ftd.assignments.socket;

import com.cooksys.ftd.assignments.socket.model.Config;
import com.cooksys.ftd.assignments.socket.model.Student;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class Server extends Utils {

	/**
	 * Reads a {@link Student} object from the given file path
	 *
	 * @param studentFilePath the file path from which to read the student config file
	 * @param jaxb the JAXB context to use during unmarshalling
	 * @return a {@link Student} object unmarshalled from the given file path
	 * @throws JAXBException 
	 */
	public static Student loadStudent(String studentFilePath, JAXBContext jaxb) throws JAXBException {
		File f = new File(studentFilePath);
		Unmarshaller unMarsh = jaxb.createUnmarshaller();
		Student student = (Student) unMarsh.unmarshal(f);
		return student;

	}

	/**
	 * The server should load a {@link com.cooksys.ftd.assignments.socket.model.Config} object from the
	 * <project-root>/config/config.xml path, using the "port" property of the embedded
	 * {@link com.cooksys.ftd.assignments.socket.model.LocalConfig} object to create a server socket that
	 * listens for connections on the configured port.
	 *
	 * Upon receiving a connection, the server should unmarshal a {@link Student} object from a file location
	 * specified by the config's "studentFilePath" property. It should then re-marshal the object to xml over the
	 * socket's output stream, sending the object to the client.
	 *
	 * Following this transaction, the server may shut down or listen for more connections.
	 * @throws IOException 
	 * @throws JAXBException 
	 */
	public static void main(String[] args) throws IOException, JAXBException {

		String cpath = "config/config.xml";
		Config config = new Config();

		JAXBContext jaxbc = Utils.createJAXBContext(); //Create jaxb context
		config = Utils.loadConfig(cpath, jaxbc); // load config object

		//get port number and stident file path from config object
		int port = config.getLocal().getPort(); 
		String studentFilePath = config.getStudentFilePath(); //

		//create server socket
		ServerSocket server = new ServerSocket(port);
//		server.setSoTimeout(60*60*5);
		Socket client = server.accept(); // server is ready for client

		//Server unmarshalls the student object by using studentFilePath
		JAXBContext jaxbs = Utils.createJAXBContext();
		Student student = loadStudent(studentFilePath, jaxbs);

		//re-marshal the
		JAXBContext jaxbss = Utils.createJAXBContext();
		Marshaller stdMarsh1 = jaxbss.createMarshaller();

		//sending xml file to client 
		stdMarsh1.marshal(student, client.getOutputStream());		

		server.close();
		client.close();


	}
}
