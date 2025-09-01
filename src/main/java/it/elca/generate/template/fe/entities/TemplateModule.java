package it.elca.generate.template.fe.entities;

import it.elca.generate.ConfigCreateProject;
import it.elca.generate.DataBase;
import it.elca.generate.Table;
import it.elca.generate.Utils;
import it.elca.generate.template.AbstractResourceTemplate;

public class TemplateModule extends AbstractResourceTemplate {

	public TemplateModule(DataBase database) {
		super(database);
	}

	public String getTypeFile() {
		return "ts";
	}

	public String getBody(){
		ConfigCreateProject conf = ConfigCreateProject.getIstance();
		// https://www.buildmystring.com/
		
		String body = 
		"import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';\r\n";
		
		for(Table table: Utils.getTables(database) ) {
			String Tablename = Utils.getEntityName(table);
			String tablename = Utils.getFieldName(table);
			body += "import { "+Utils.getClassNameCamelCase(conf.getProjectName()) + Tablename+"Module } from './"+tablename+"/"+tablename+".module';\r\n" ;
		}
		body += "\r\n" +
		"@NgModule({\r\n" +
		"    // prettier-ignore\r\n" +
		"    imports: [\r\n" ;
		for(Table table: Utils.getTables(database) ) {
			String Tablename = Utils.getEntityName(table);
			body += "        "+Utils.getClassNameCamelCase(conf.getProjectName()) +Tablename+"Module,\r\n" ;
		}
		body +=
		"    ],\r\n" +
		"    declarations: [],\r\n" +
		"    entryComponents: [],\r\n" +
		"    providers: [],\r\n" +
		"    schemas: [CUSTOM_ELEMENTS_SCHEMA]\r\n" +
		"})\r\n" +
		"export class "+Utils.getClassNameCamelCase(conf.getProjectName()) +"EntityModule {}\r\n";
		return body;
	}
	
	public String getClassName(){
		return "entity.module";
	}

	@Override
	public String getTypeTemplate() {
		String typeTemplate = "";
		return typeTemplate;
	}

	@Override
	public String getSourceFolder() {
		return "src/main/webapp/app/entities";
	}

}
