package it.elca;

import java.io.IOException;

import it.elca.generate.DataBase;

public class App 
{
	/**
	 * Smart Generator Main Procedure.
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		DataBase db = DataBase.getInstance();
		db.generateFile();
	}
}
