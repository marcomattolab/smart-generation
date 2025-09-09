package it.elca.generate.template.service;

import it.elca.generate.Column;
import it.elca.generate.ConfigCreateProject;
import it.elca.generate.ProjectRelation;
import it.elca.generate.Table;
import it.elca.generate.Utils;
import it.elca.generate.template.AbstractTemplate;
import it.elca.generate.template.FreemarkerTemplate;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TemplateServiceCriteria extends AbstractTemplate {

    public TemplateServiceCriteria(Table tabella) {
        super(tabella);
    }

    @Override
    public String getBody() {
        ConfigCreateProject conf = ConfigCreateProject.getIstance();
        Map<String, Object> data = new HashMap<>();
        data.put("packageName", conf.getPackageclass());
        data.put("dtoPackage", conf.getSrcServiceDtoFolder());
        data.put("entityName", Utils.getEntityName(tabella));
        data.put("entityFieldName", Utils.getFieldName(tabella));
        data.put("className", getClassName());

        List<Map<String, String>> fields = new ArrayList<>();
        List<Column> extendedList = new ArrayList<>(tabella.getColumns());

        if (!CollectionUtils.isEmpty(conf.getProjectRelations())) {
            for (ProjectRelation rel : conf.getProjectRelations()) {
                addRelationFields(extendedList, rel);
            }
        }

        for (Column column : extendedList) {
            if (!Utils.isBlob(column) && !Utils.isClob(column)) {
                Map<String, String> fieldData = new HashMap<>();
                fieldData.put("name", Utils.getFieldName(column));
                fieldData.put("capitalizedName", Utils.getFieldNameForMethod(column));
                fieldData.put("type", Utils.getFilterType(column));
                fields.add(fieldData);
            }
        }
        data.put("fields", fields);

        return FreemarkerTemplate.process("service/criteria.java.ftl", data);
    }

    private void addRelationFields(List<Column> extendedList, ProjectRelation rel) {
        String relationType = rel.getType();
        String sxTable = rel.getSxTable().toLowerCase();
        String dxTable = rel.getDxTable().toLowerCase();
        String currentTable = tabella.getNomeTabella().toLowerCase();

        if (sxTable.equals(currentTable) || dxTable.equals(currentTable)) {
            String relationName = null;
            if (sxTable.equals(currentTable)) {
                relationName = rel.getSxName();
            } else if (dxTable.equals(currentTable) && (relationType.equals(Utils.ManyToMany) || relationType.equals(Utils.OneToMany))) {
                relationName = rel.getDxName();
            }

            if (relationName != null) {
                Column column = new Column();
                column.setName(relationName + "Id");
                column.setTypeColumn(Column.corvertModelType("LongFilter"));
                extendedList.add(column);
            }
        }
    }

    public String getClassName() {
        return Utils.getCriteriaClassName(tabella);
    }

    @Override
    public String getTypeTemplate() {
        return ConfigCreateProject.getIstance().getSrcServiceDtoFolder().replace(".", "/");
    }

    @Override
    public String getTypeFile() {
        return "java";
    }

    @Override
    public String getSourceFolder() {
        return "src/main/java";
    }
}
