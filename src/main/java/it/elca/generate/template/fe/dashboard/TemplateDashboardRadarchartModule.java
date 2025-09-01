package it.elca.generate.template.fe.dashboard;

import it.elca.generate.ConfigCreateProject;
import it.elca.generate.DataBase;
import it.elca.generate.Utils;
import it.elca.generate.template.AbstractResourceTemplate;

public class TemplateDashboardRadarchartModule extends AbstractResourceTemplate {

	public TemplateDashboardRadarchartModule(DataBase database) {
		super(database);
	}

	public String getTypeFile() {
		return "ts";
	}

	public String getBody(){
		ConfigCreateProject conf = ConfigCreateProject.getIstance();
		// https://www.buildmystring.com/
		String body = 
		"import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';\r\n" +
		"import { RouterModule } from '@angular/router';\r\n" +
		"import { "+Utils.getClassNameCamelCase(conf.getProjectName())+"SharedModule } from '../../shared';\r\n" +
		"import { ChartModule } from 'primeng/primeng';\r\n" +
		"import {\r\n" +
		"    RadarchartComponent,\r\n" +
		"    radarchartRoute\r\n" +
		"} from './';\r\n" +
		"const DASHBOARD_STATES = [\r\n" +
		"    radarchartRoute\r\n" +
		"];\r\n" +
		"@NgModule({\r\n" +
		"    imports: [\r\n" +
		"        "+Utils.getClassNameCamelCase(conf.getProjectName())+"SharedModule,\r\n" +
		"        ChartModule,\r\n" +
		"        RouterModule.forRoot(DASHBOARD_STATES, { useHash: true })\r\n" +
		"    ],\r\n" +
		"    declarations: [\r\n" +
		"        RadarchartComponent\r\n" +
		"    ],\r\n" +
		"    schemas: [CUSTOM_ELEMENTS_SCHEMA]\r\n" +
		"})\r\n" +
		"export class "+Utils.getClassNameCamelCase(conf.getProjectName())+"RadarchartModule {}\r\n";
		return body;
	}

	public String getClassName(){
		return "radarchart.module";
	}

	@Override
	public String getTypeTemplate() {
		String typeTemplate = "";
		return typeTemplate;
	}

	@Override
	public String getSourceFolder() {
		return "src/main/webapp/app/dashboard/radarchart";
	}

}
