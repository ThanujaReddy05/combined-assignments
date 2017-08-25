package com.cooksys.ftd.assignments.collections;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Day4Client {

	public static void main(String[] args) throws UnknownHostException, IOException  
	{
		Socket socket = new Socket("10.1.1.127", 4455);
		PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream())) ;
		writer.println("Thanu");
		BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		writer.println(Integer.valueOf(reader.readLine())*2);	
		
		
		writer.flush();
		writer.close();
			
			
	}
}
