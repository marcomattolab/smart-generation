package it.elca.generate.template.resouces;

import it.elca.generate.Column;
import it.elca.generate.ConfigCreateProject;
import it.elca.generate.DataBase;
import it.elca.generate.Table;
import it.elca.generate.Utils;
import it.elca.generate.template.AbstractResourceTemplate;

public class TemplateLiquidbaseData extends AbstractResourceTemplate{
	private static final int ITERATION_COUNT = 10; 
	private static final String CREATE_DATE = "2020-02-01"; 
	private static final String CREATE_USER = "user"; 
	
	public TemplateLiquidbaseData(DataBase database, Table tabella) {
		super(database);
		this.tabella = tabella;
	}

	public String getTypeTemplate() {
		String typeTemplate = Utils.replace(ConfigCreateProject.getIstance().getResConfigLiquibaseDataFolder(),".","/");
		return typeTemplate;
	}
	
	public String getTypeFile() {
		return "csv";
	}

	public String getBody() {
		ConfigCreateProject conf = ConfigCreateProject.getIstance();
		String body = "";
		
		//Header CSV
		int i = 0;
		int s = tabella.getColumns().size();
		for (Column column: tabella.getColumns()) {
			if (Utils.isBlob(column) || Utils.isClob(column)) {
				String nomeColonna = column.getName();
				body += nomeColonna + (i+1<s ? ";" : ";");
				body += nomeColonna+"_content_type" + (i+1<s ? ";" : ";");
				i++;
			} else {
				String nomeColonna = column.getName();
				body += nomeColonna + (i+1<s ? ";" : ";");
				i++;
			}
			
		}
		body += "created_by;created_date\n";

		
		//Rows CSV
		for(int a=0; a<ITERATION_COUNT; a++) {
			int k=0;
			for (Column column: tabella.getColumns()) {
				String ctype = column.getLabelType().toLowerCase();
				String classes = column.getTypeColumn().getName();
				
				//TODO ADD PATTERN VALIDATION
				if (Utils.isBlob(column) || Utils.isClob(column)) {
					body += Utils.getRandomBlob();
					body += ";" + Utils.getRandomBlobContentType();
				} else if ("date".equals(ctype)) {
			    		body += Utils.getRandomDate();
			    	} else if("string".equals(ctype)) {
					body +=  column.getEnumeration()!=null
						 ? Utils.getRandomEnumeration(column, tabella, database)
						 : Utils.getRandomString(column);
				} else if ("integer".equals(ctype)) {
					body += a+1;
				} else if ("numeric".equals(ctype)) {
					body += a+101;
				} else if ("boolean".equals(ctype)) {
					body += Utils.getRandomBoolean();
				}
				body += (k+1) < tabella.getColumns().size() ? ";" : ";";
				k++;
			}
			body += ""+CREATE_USER+";"+CREATE_DATE+"\n"; //TODO MANAGE create_by, create_date
		}

		return body;
	}

	public String getClassName() {
		String entityname = Utils.getClassNameLowerCase(tabella);
		return entityname +"s";
	}
	
	public String getSourceFolder() {
		return "src/main/resources";
	}

}
