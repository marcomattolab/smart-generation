package it.elca.generate.template.fe.entities;

import it.elca.generate.Column;
import it.elca.generate.ConfigCreateProject;
import it.elca.generate.DataBase;
import it.elca.generate.ProjectRelation;
import it.elca.generate.Table;
import it.elca.generate.Utils;
import it.elca.generate.template.AbstractResourceTemplate;
import it.elca.generate.template.FreemarkerTemplate;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TemplateEntityUpdateComponentHtml extends AbstractResourceTemplate {

	public TemplateEntityUpdateComponentHtml(Table tabella) {
		super(tabella);
	}

	public TemplateEntityUpdateComponentHtml(DataBase database, Table tabella) {
		super(database);
		this.tabella = tabella;
	}

	@Override
	public String getBody() {
		ConfigCreateProject conf = ConfigCreateProject.getIstance();
		Map<String, Object> data = super.getMapData();
		data.put("projectName", conf.getProjectName());
		data.put("entityName", Utils.getEntityName(tabella));
		data.put("classNameLowerCase", Utils.getClassNameLowerCase(tabella));

		List<Map<String, String>> columns = new ArrayList<>();
		for (Column column : tabella.getSortedColumns()) {
			Map<String, String> columnData = new HashMap<>();
			columnData.put("html", Utils.getTemplateHtmlByType(database, column, tabella, conf));
			columns.add(columnData);
		}
		data.put("columns", columns);

		if (!CollectionUtils.isEmpty(conf.getProjectRelations())) {
			List<Map<String, Object>> relations = new ArrayList<>();
			for (ProjectRelation rel : conf.getProjectRelations()) {
				Map<String, Object> relData = new HashMap<>();
				String nomeTabella = tabella.getNomeTabella().toLowerCase();
				String nomeTabellaSx = rel.getSxTable().toLowerCase();

				if (nomeTabellaSx.equals(nomeTabella) || rel.getDxTable().toLowerCase().equals(nomeTabella)) {
					relData.put("relationType", rel.getType());
					relData.put("relationshipName", rel.getSxName());
					relData.put("relationshipNameCapitalized", Utils.getFirstUpperCase(rel.getSxName()));
					relData.put("otherEntityNameCapitalized", Utils.getFirstUpperCase(rel.getDxTable()));
					relData.put("otherEntityField", rel.getSxSelect());
					relData.put("ownerSide", nomeTabellaSx.equals(nomeTabella));
					relData.put("otherEntityName", rel.getDxTable());
					relations.add(relData);
				}
			}
			data.put("relations", relations);
		}

		return FreemarkerTemplate.process("fe/entities/entity-update.component.html.ftl", data);
	}

	public String getClassName() {
		return Utils.getClassNameLowerCase(tabella) + "-update.component";
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
		return "src/main/webapp/app/entities/" + Utils.getClassNameLowerCase(tabella);
	}
}
