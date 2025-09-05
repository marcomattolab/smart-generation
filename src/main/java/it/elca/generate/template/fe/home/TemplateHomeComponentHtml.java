package it.elca.generate.template.fe.home;

import it.elca.generate.ConfigCreateProject;
import it.elca.generate.DataBase;
import it.elca.generate.Utils;
import it.elca.generate.template.AbstractResourceTemplate;
import it.elca.generate.template.FreemarkerTemplate;

import java.util.HashMap;
import java.util.Map;

public class TemplateHomeComponentHtml extends AbstractResourceTemplate {

	public TemplateHomeComponentHtml(DataBase database) {
		super(database);
	}

	@Override
	public String getBody() {
		ConfigCreateProject conf = ConfigCreateProject.getIstance();
		Map<String, Object> data = new HashMap<>();
		data.put("authorities", Utils.getGlobalAuthoritiesCredential(conf));
		return FreemarkerTemplate.process("fe/home/home.component.html.ftl", data);
	}

	public String getClassName() {
		return "home.component";
	}

	@Override
	public String getTypeFile() {
		return "html";
	}

	@Override
	public String getTypeTemplate() {
		return "";
	}

	@Override
	public String getSourceFolder() {
		return "src/main/webapp/app/home";
	}
}
