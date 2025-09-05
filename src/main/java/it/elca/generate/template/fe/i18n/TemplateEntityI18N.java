package it.elca.generate.template.fe.i18n;

import it.elca.generate.Column;
import it.elca.generate.ConfigCreateProject;
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

public class TemplateEntityI18N extends AbstractResourceTemplate {
    private String languageCode;

    public TemplateEntityI18N(Table tabella) {
        super(tabella);
    }

    public TemplateEntityI18N(Table tabella, String languageCode) {
        super(tabella);
        this.languageCode = languageCode;
    }

    @Override
    public String getBody() {
        ConfigCreateProject conf = ConfigCreateProject.getIstance();
        Map<String, Object> data = super.getMapData();
        data.put("projectName", conf.getProjectName());
        data.put("entityName", Utils.getEntityName(tabella));
        data.put("classNameLowerCase", Utils.getFieldName(tabella));

        List<Map<String, String>> columns = new ArrayList<>();
        for (Object o : tabella.getColumnNames()) {
            String key = (String) o;
            Column column = tabella.getColumn(key);
            Map<String, String> columnData = new HashMap<>();
            columnData.put("name", Utils.getFieldName(column));
            columnData.put("capitalizedName", Utils.getLabel(column.getName()));
            columns.add(columnData);
        }
        data.put("columns", columns);

        if (!CollectionUtils.isEmpty(conf.getProjectRelations())) {
            List<Map<String, String>> relations = new ArrayList<>();
            for (ProjectRelation rel : conf.getProjectRelations()) {
                String nomeTabella = tabella.getNomeTabella().toLowerCase();
                String nomeTabellaSx = rel.getSxTable().toLowerCase();

                if (nomeTabellaSx.equals(nomeTabella) || rel.getDxTable().toLowerCase().equals(nomeTabella)) {
                    Map<String, String> relData = new HashMap<>();
                    String name = "";
                    if (rel.getType().equals(Utils.OneToMany) && rel.getDxTable().toLowerCase().equals(nomeTabella)) {
                        name = rel.getDxName();
                    } else if (nomeTabellaSx.equals(nomeTabella)) {
                        name = rel.getSxName();
                    }
                    if (!name.isEmpty()) {
                        relData.put("name", name);
                        relData.put("capitalizedName", Utils.getLabel(name));
                        relations.add(relData);
                    }
                }
            }
            data.put("relations", relations);
        }

        return FreemarkerTemplate.process("fe/i18n/entity.json.ftl", data);
    }

    public String getClassName() {
        return Utils.getClassNameLowerCase(tabella);
    }

    @Override
    public String getTypeFile() {
        return "json";
    }

    @Override
    public String getTypeTemplate() {
        return "";
    }

    @Override
    public String getSourceFolder() {
        return "src/main/webapp/i18n/" + languageCode;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }
}
