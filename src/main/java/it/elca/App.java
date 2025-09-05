package it.elca;

import it.elca.generate.DataBase;

public class App 
{
	/**
	 * Smart Generator Main Procedure
	 */
	public static void main(String[] args) {
		DataBase db = DataBase.getInstance();
		db.generateFile();
	}
}
