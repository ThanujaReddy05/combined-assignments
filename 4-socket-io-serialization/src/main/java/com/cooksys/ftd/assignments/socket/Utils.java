package com.cooksys.ftd.assignments.socket;

import com.cooksys.ftd.assignments.socket.model.Config;
import com.cooksys.ftd.assignments.socket.model.Student;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * Shared static methods to be used by both the {@link Client} and {@link Server} classes.
 */
public class Utils {
	/**
	 * @return a {@link JAXBContext} initialized with the classes in the
	 * com.cooksys.socket.assignment.model package
	 */
	public static JAXBContext createJAXBContext() {
		JAXBContext context = null;
		try{
			//create jaxb contex for student and config class
			context = JAXBContext.newInstance(new Class[]{Student.class, Config.class} ); 
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return context; 
	}

	/**
	 * Reads a {@link Config} object from the given file path.
	 *
	 * @param configFilePath the file path to the config.xml file
	 * @param jaxb the JAXBContext to use
	 * @return a {@link Config} object that was read from the config.xml file
	 * @throws JAXBException 
	 */
	public static Config loadConfig(String configFilePath, JAXBContext jaxb) throws JAXBException {
		File f = new File(configFilePath);
		Unmarshaller unMarsh = jaxb.createUnmarshaller();
		Config config = (Config) unMarsh.unmarshal(f);
		return config;
	}
}
