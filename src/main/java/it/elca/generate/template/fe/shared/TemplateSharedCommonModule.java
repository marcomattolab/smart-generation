package it.elca.generate.template.fe.shared;

import it.elca.generate.ConfigCreateProject;
import it.elca.generate.DataBase;
import it.elca.generate.Utils;
import it.elca.generate.template.AbstractResourceTemplate;
import it.elca.generate.template.FreemarkerTemplate;

import java.util.HashMap;
import java.util.Map;

public class TemplateSharedCommonModule extends AbstractResourceTemplate {

	public TemplateSharedCommonModule(DataBase database) {
		super(database);
	}

	@Override
	public String getBody() {
		ConfigCreateProject conf = ConfigCreateProject.getIstance();
		Map<String, Object> data = new HashMap<>();
		data.put("projectNameCamelCase", Utils.getClassNameCamelCase(conf.getProjectName()));
		return FreemarkerTemplate.process("fe/shared/shared-common.module.ts.ftl", data);
	}

	public String getClassName() {
		return "shared-common.module";
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
		return "src/main/webapp/app/shared";
	}
}
