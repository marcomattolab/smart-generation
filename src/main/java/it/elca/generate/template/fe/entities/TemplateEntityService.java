package it.elca.generate.template.fe.entities;

import it.elca.generate.Column;
import it.elca.generate.Table;
import it.elca.generate.Utils;
import it.elca.generate.template.AbstractResourceTemplate;
import it.elca.generate.template.FreemarkerTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TemplateEntityService extends AbstractResourceTemplate {

	public TemplateEntityService(Table tabella) {
		super(tabella);
	}

	@Override
	public String getBody() {
		Map<String, Object> data = super.getMapData();
		data.put("iName", Utils.getIName(tabella));
		data.put("classNameLowerCase", Utils.getClassNameLowerCase(tabella));
		data.put("entityName", Utils.getEntityName(tabella));

		List<Map<String, Object>> dateFields = new ArrayList<>();
		for (Object o : tabella.getColumnNames()) {
			String key = (String) o;
			Column column = tabella.getColumn(key);
			if (Utils.isDateField(column)) {
				Map<String, Object> field = new HashMap<>();
				field.put("fieldName", Utils.getFieldName(column));
				field.put("localDate", Utils.isLocalDate(column));
				dateFields.add(field);
			}
		}
		data.put("dateFields", dateFields);

		return FreemarkerTemplate.process("fe/entities/entity-service.ts.ftl", data);
	}

	public String getClassName(){
		return Utils.getClassNameLowerCase(tabella)+".service";
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
