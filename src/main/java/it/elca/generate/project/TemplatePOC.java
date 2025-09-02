package it.elca.generate.project;

import it.elca.generate.*;
import it.elca.generate.template.AbstractResourceTemplate;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

//TODO THIS IS THE MOCK TO USE files ftl (See FreeMarker  https://freemarker.apache.org/index.html )
public class TemplatePOC extends AbstractResourceTemplate {

	public TemplatePOC(DataBase database, Table tabella) {
		super(database);
		this.tabella = tabella;
	}

	@Override
	public String getTypeFile() {
		return "html";
	}

	@Override
	public String getBody() {
		try {
			Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);
			cfg.setClassForTemplateLoading(this.getClass(), "/templates/");
			cfg.setDefaultEncoding("UTF-8");
			Template template = cfg.getTemplate("poc.ftl");

			ConfigCreateProject conf = ConfigCreateProject.getIstance();
			String entityVar = Utils.getClassNameLowerCase(tabella);
			String entityName = Utils.getEntityName(tabella);

			Map<String, Object> dataModel = new HashMap<>();
			dataModel.put("projectName", conf.getProjectName());
			dataModel.put("entityName", entityName);
			dataModel.put("entityVar", entityVar);
			dataModel.put("columns", tabella.getSortedColumns());
			dataModel.put("relations", conf.getProjectRelations());

			StringWriter out = new StringWriter();
			template.process(dataModel, out);
			return out.toString();

		} catch (IOException | TemplateException e) {
			throw new RuntimeException("Errore durante la generazione del template FreeMarker", e);
		}
	}

	@Override
	public String getClassName() {
		return Utils.getClassNameLowerCase(tabella) + ".component";
	}

	@Override
	public String getTypeTemplate() {
		return "";
	}

	@Override
	public String getSourceFolder() {
		return "src/main/webapp/app/entities/POC-" + Utils.getClassNameLowerCase(tabella);
	}
}