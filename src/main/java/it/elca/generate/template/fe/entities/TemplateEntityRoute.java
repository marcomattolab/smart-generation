package it.elca.generate.template.fe.entities;

import it.elca.generate.ConfigCreateProject;
import it.elca.generate.Table;
import it.elca.generate.Utils;
import it.elca.generate.template.AbstractResourceTemplate;
import it.elca.generate.template.FreemarkerTemplate;

import java.util.Map;

public class TemplateEntityRoute extends AbstractResourceTemplate {

	public TemplateEntityRoute(Table tabella) {
		super(tabella);
	}

	@Override
	public String getBody() {
		ConfigCreateProject conf = ConfigCreateProject.getIstance();
		Map<String, Object> data = super.getMapData();
		data.put("entityName", Utils.getEntityName(tabella));
		data.put("classNameLowerCase", Utils.getClassNameLowerCase(tabella));
		data.put("iName", Utils.getIName(tabella));
		data.put("authorities", Utils.getAuthorities(tabella, Utils.APICE));
		data.put("projectName", conf.getProjectName());
		return FreemarkerTemplate.process("fe/entities/entity-route.ts.ftl", data);
	}

	public String getClassName(){
		return Utils.getClassNameLowerCase(tabella)+".route";
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
		return "src/main/webapp/app/entities/"+Utils.getClassNameLowerCase(tabella);
	}

}
