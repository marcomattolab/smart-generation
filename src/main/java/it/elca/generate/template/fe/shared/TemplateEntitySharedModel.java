package it.elca.generate.template.fe.shared;

import it.elca.generate.Column;
import it.elca.generate.ConfigCreateProject;
import it.elca.generate.DataBase;
import it.elca.generate.Enumeration;
import it.elca.generate.ProjectRelation;
import it.elca.generate.Table;
import it.elca.generate.Utils;
import it.elca.generate.template.AbstractResourceTemplate;
import it.elca.generate.template.FreemarkerTemplate;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TemplateEntitySharedModel extends AbstractResourceTemplate {

    public TemplateEntitySharedModel(Table tabella) {
        super(tabella);
    }

    public TemplateEntitySharedModel(DataBase database, Table tabella) {
        super(database);
        this.tabella = tabella;
    }

    @Override
    public String getBody() {
        ConfigCreateProject conf = ConfigCreateProject.getIstance();
        Map<String, Object> data = super.getMapData();
        data.put("iName", "I" + Utils.getEntityName(tabella));
        data.put("className", Utils.getEntityName(tabella));

        // Enumerations
        List<Map<String, Object>> enumerations = new ArrayList<>();
        for (Enumeration enumeration : Utils.getEnumerationsByDbAndTable(database, tabella)) {
            Map<String, Object> enumData = new HashMap<>();
            enumData.put("name", enumeration.getNomeEnumeration());
            enumData.put("values", enumeration.getValoriEnumeration());
            enumerations.add(enumData);
        }
        data.put("enumerations", enumerations);

        // Imports and Fields
        Set<String> imports = new HashSet<>();
        List<Map<String, String>> fields = new ArrayList<>();
        processRelationsAndFields(conf, imports, fields);
        data.put("imports", imports);
        data.put("fields", fields);

        return FreemarkerTemplate.process("fe/shared/entity.model.ts.ftl", data);
    }

    private void processRelationsAndFields(ConfigCreateProject conf, Set<String> imports, List<Map<String, String>> fields) {
        // Initial fields from table columns
        for (Column column : tabella.getColumns()) {
            fields.add(createField(Utils.getFieldName(column), column.getTypescriptType()));
        }

        // Fields and imports from relations
        if (!CollectionUtils.isEmpty(conf.getProjectRelations())) {
            for (ProjectRelation rel : conf.getProjectRelations()) {
                String relationType = rel.getType();
                String sxTable = rel.getSxTable().toLowerCase();
                String dxTable = rel.getDxTable().toLowerCase();
                String currentTable = tabella.getNomeTabella().toLowerCase();

                if (sxTable.equals(currentTable) || dxTable.equals(currentTable)) {
                    if (relationType.equals(Utils.OneToOne) || relationType.equals(Utils.ManyToOne)) {
                        if (sxTable.equals(currentTable)) {
                            fields.add(createField(rel.getSxName() + "Id", "number"));
                            if (relationType.equals(Utils.ManyToOne)) {
                                fields.add(createField(rel.getSxName() + Utils.getFirstUpperCase(rel.getSxSelect()),
                                        Utils.getTypeColumnFromRelation(conf, rel.getSxSelect(), rel.getDxTable())));
                            }
                        }
                    } else if (relationType.equals(Utils.ManyToMany)) {
                        if (sxTable.equals(currentTable)) {
                            imports.add(getImportStatement(rel.getDxTable()));
                            fields.add(createField(rel.getSxName() + "s", "I" + Utils.getFirstUpperCase(rel.getDxTable()) + "[]"));
                        }
                        if (dxTable.equals(currentTable)) {
                            imports.add(getImportStatement(rel.getSxTable()));
                            fields.add(createField(rel.getDxName() + "s", "I" + Utils.getFirstUpperCase(rel.getSxTable()) + "[]"));
                        }
                    }
                }
            }
        }
    }

    private Map<String, String> createField(String name, String type) {
        Map<String, String> field = new HashMap<>();
        field.put("name", name);
        field.put("type", type);
        return field;
    }

    private String getImportStatement(String otherTable) {
        return "import { I" + Utils.getFirstUpperCase(otherTable) + " } from 'app/shared/model/" + otherTable.toLowerCase() + ".model';";
    }

    public String getClassName() {
        return Utils.getClassNameLowerCase(tabella) + ".model";
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
        return "src/main/webapp/app/shared/model";
    }
}
