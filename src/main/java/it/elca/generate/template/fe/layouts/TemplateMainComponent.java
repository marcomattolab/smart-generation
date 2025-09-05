package it.elca.generate.template.fe.layouts;

import it.elca.generate.ConfigCreateProject;
import it.elca.generate.DataBase;
import it.elca.generate.template.AbstractResourceTemplate;
import it.elca.generate.template.FreemarkerTemplate;

import java.util.HashMap;
import java.util.Map;

public class TemplateMainComponent extends AbstractResourceTemplate {

	public TemplateMainComponent(DataBase database) {
		super(database);
	}

	@Override
	public String getBody() {
		ConfigCreateProject conf = ConfigCreateProject.getIstance();
		Map<String, Object> data = new HashMap<>();
		data.put("projectName", conf.getProjectName());
		return FreemarkerTemplate.process("fe/layouts/main.component.ts.ftl", data);
	}

	public String getClassName() {
		return "main.component";
	}

	@Override
	public String getTypeFile() {
		return "ts";
	}

	@Override
	public String getTypeTemplate() {
		return "";
	}

	@Override
	public String getSourceFolder() {
		return "src/main/webapp/app/layouts/main";
	}
}
