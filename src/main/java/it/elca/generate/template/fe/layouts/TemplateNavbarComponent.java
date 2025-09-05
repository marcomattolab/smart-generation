package it.elca.generate.template.fe.layouts;

import it.elca.generate.ConfigCreateProject;
import it.elca.generate.DataBase;
import it.elca.generate.Table;
import it.elca.generate.Utils;
import it.elca.generate.template.AbstractResourceTemplate;
import it.elca.generate.template.FreemarkerTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TemplateNavbarComponent extends AbstractResourceTemplate {

	public TemplateNavbarComponent(DataBase database) {
		super(database);
	}

	@Override
	public String getBody() {
		ConfigCreateProject conf = ConfigCreateProject.getIstance();
		Map<String, Object> data = new HashMap<>();
		data.put("projectNameCamelCase", Utils.getClassNameCamelCase(conf.getProjectName()));

		List<Map<String, String>> tables = new ArrayList<>();
		for (Table table : Utils.getTables(database)) {
			Map<String, String> tableData = new HashMap<>();
			tableData.put("authorities", Utils.getAuthorities(table, Utils.APICE));
			tableData.put("fieldName", Utils.getFieldName(table));
			tableData.put("entityName", Utils.getEntityName(table));
			tables.add(tableData);
		}
		data.put("tables", tables);

		return FreemarkerTemplate.process("fe/layouts/navbar.component.html.ftl", data);
	}

	public String getClassName() {
		return "navbar.component";
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
		return "src/main/webapp/app/layouts/navbar";
	}
}
