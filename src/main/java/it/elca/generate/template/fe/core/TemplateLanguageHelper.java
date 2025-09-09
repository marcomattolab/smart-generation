package it.elca.generate.template.fe.core;

import it.elca.generate.ConfigCreateProject;
import it.elca.generate.DataBase;
import it.elca.generate.template.AbstractResourceTemplate;
import it.elca.generate.template.FreemarkerTemplate;

import java.util.HashMap;
import java.util.Map;

public class TemplateLanguageHelper extends AbstractResourceTemplate {

    public TemplateLanguageHelper(DataBase database) {
        super(database);
    }

    public String getTypeFile() {
        return "ts";
    }

    public String getBody() {
        ConfigCreateProject conf = ConfigCreateProject.getIstance();
        Map<String, Object> data = new HashMap<>();
        data.put("projectName", conf.getProjectName());
        return FreemarkerTemplate.process("fe/core/language.helper.ts.ftl", data);
    }

    public String getClassName() {
        return "language.helper";
    }

    @Override
    public String getTypeTemplate() {
        String typeTemplate = "";
        return typeTemplate;
    }

    @Override
    public String getSourceFolder() {
        return "src/main/webapp/app/core/language";
    }

}
