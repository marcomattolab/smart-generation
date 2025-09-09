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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TemplateServiceDTO extends AbstractTemplate {

    public TemplateServiceDTO(Table tabella) {
        super(tabella);
    }

    @Override
    public String getBody() {
        ConfigCreateProject conf = ConfigCreateProject.getIstance();
        Map<String, Object> data = new HashMap<>();
        data.put("packageName", conf.getPackageclass());
        data.put("dtoPackage", conf.getSrcServiceDtoFolder());
        data.put("entityName", Utils.getEntityName(tabella));
        data.put("className", getClassName());

        Set<String> imports = new HashSet<>();
        List<Map<String, Object>> fields = new ArrayList<>();

        // Process columns and relations
        processColumnsAndRelations(conf, fields, imports);

        data.put("imports", imports);
        data.put("fields", fields);

        return FreemarkerTemplate.process("service/dto.java.ftl", data);
    }

    private void processColumnsAndRelations(ConfigCreateProject conf, List<Map<String, Object>> fields, Set<String> imports) {
        // Add fields from table columns
        for (Column column : tabella.getColumns()) {
            fields.add(createField(column));
        }

        // Add fields from relations
        if (!CollectionUtils.isEmpty(conf.getProjectRelations())) {
            for (ProjectRelation rel : conf.getProjectRelations()) {
                addRelationFields(fields, rel, conf);
            }
        }
    }

    private Map<String, Object> createField(Column column) {
        Map<String, Object> field = new HashMap<>();
        field.put("name", Utils.getFieldName(column));
        field.put("capitalizedName", Utils.getFieldNameForMethod(column));
        field.put("type", column.getTypeName());
        field.put("annotations", Utils.getAnnotationAsList(column));
        return field;
    }

    private void addRelationFields(List<Map<String, Object>> fields, ProjectRelation rel, ConfigCreateProject conf) {
        String relationType = rel.getType();
        String sxTable = rel.getSxTable().toLowerCase();
        String dxTable = rel.getDxTable().toLowerCase();
        String currentTable = tabella.getNomeTabella().toLowerCase();

        if (sxTable.equals(currentTable) || dxTable.equals(currentTable)) {
            if (relationType.equals(Utils.OneToOne) || relationType.equals(Utils.ManyToOne)) {
                if (sxTable.equals(currentTable)) {
                    fields.add(createRelationField(rel.getSxName() + "Id", "Long"));
                    fields.add(createRelationField(rel.getSxName() + Utils.getFirstUpperCase(rel.getSxSelect()), Utils.getTypeColumnFromRelation(conf, rel.getSxSelect(), rel.getDxTable())));
                }
            } else if (relationType.equals(Utils.OneToMany)) {
                if (dxTable.equals(currentTable)) {
                    fields.add(createRelationField(rel.getDxName() + "Id", "Long"));
                    fields.add(createRelationField(rel.getDxName() + Utils.getFirstUpperCase(rel.getDxSelect()), Utils.getTypeColumnFromRelation(conf, rel.getDxSelect(), rel.getSxTable())));
                }
            } else if (relationType.equals(Utils.ManyToMany)) {
                if (sxTable.equals(currentTable)) {
                    fields.add(createRelationField(Utils.getFirstLowerCase(rel.getSxName()) + "s", "Set<" + Utils.getFirstUpperCase(dxTable) + "DTO>"));
                }
            }
        }
    }

    private Map<String, Object> createRelationField(String name, String type) {
        Map<String, Object> field = new HashMap<>();
        field.put("name", name);
        field.put("capitalizedName", Utils.getFirstUpperCase(name));
        field.put("type", type);
        field.put("annotations", new ArrayList<String>()); // No annotations for relation fields in DTO
        return field;
    }


    public String getClassName() {
        return Utils.getDTOClassName(tabella);
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
