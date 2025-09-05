package it.elca.generate.template.fe.i18n;

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

public class TemplateGlobalI18N extends AbstractResourceTemplate {
    private String languageCode;

    public TemplateGlobalI18N(DataBase database) {
        super(database);
    }

    public TemplateGlobalI18N(DataBase database, String languageCode) {
        super(database);
        this.languageCode = languageCode;
    }

    @Override
    public String getBody() {
        ConfigCreateProject conf = ConfigCreateProject.getIstance();
        Map<String, Object> data = new HashMap<>();
        data.put("projectNameCamelCase", Utils.getClassNameCamelCase(conf.getProjectName()));
        data.put("authorities", Utils.getGlobalAuthoritiesCredential(conf));

        List<Map<String, String>> tables = new ArrayList<>();
        for (Table table : Utils.getTables(database)) {
            Map<String, String> tableData = new HashMap<>();
            tableData.put("classNameLowerCase", Utils.getClassNameLowerCase(table));
            tableData.put("entityName", Utils.getEntityName(table));
            tables.add(tableData);
        }
        data.put("tables", tables);

        return FreemarkerTemplate.process("fe/i18n/global.json.ftl", data);
    }

    public String getClassName() {
        return "global";
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
