package it.elca.generate.template.fe.shared;

import it.elca.generate.ConfigCreateProject;
import it.elca.generate.DataBase;
import it.elca.generate.Utils;
import it.elca.generate.template.AbstractResourceTemplate;

public class TemplateSharedCommonModule extends AbstractResourceTemplate {

	public TemplateSharedCommonModule(DataBase database) {
		super(database);
	}

	public String getTypeFile() {
		return "ts";
	}

	public String getBody(){
		ConfigCreateProject conf = ConfigCreateProject.getIstance();
		// https://www.buildmystring.com/
		String body = "import { NgModule } from '@angular/core';\r\n" +
		"import { "+Utils.getClassNameCamelCase(conf.getProjectName()) +"SharedLibsModule, FindLanguageFromKeyPipe, JhiAlertComponent, JhiAlertErrorComponent } from './';\r\n" +
		"@NgModule({\r\n" +
		"    imports: ["+Utils.getClassNameCamelCase(conf.getProjectName()) +"SharedLibsModule],\r\n" +
		"    declarations: [FindLanguageFromKeyPipe, JhiAlertComponent, JhiAlertErrorComponent],\r\n" +
		"    exports: ["+Utils.getClassNameCamelCase(conf.getProjectName()) +"SharedLibsModule, FindLanguageFromKeyPipe, JhiAlertComponent, JhiAlertErrorComponent]\r\n" +
		"})\r\n" +
		"export class "+Utils.getClassNameCamelCase(conf.getProjectName()) +"SharedCommonModule {}\r\n";
		return body;
	}

	public String getClassName(){
		return "shared-common.module";
	}

	@Override
	public String getTypeTemplate() {
		String typeTemplate = "";
		return typeTemplate;
	}

	@Override
	public String getSourceFolder() {
		return "src/main/webapp/app/shared";
	}

}
