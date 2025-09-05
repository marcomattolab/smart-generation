package it.elca.generate.template.fe;

import it.elca.generate.ConfigCreateProject;
import it.elca.generate.DataBase;
import it.elca.generate.template.AbstractResourceTemplate;
import it.elca.generate.template.FreemarkerTemplate;

import java.util.HashMap;
import java.util.Map;

public class TemplateIndex extends AbstractResourceTemplate {

    public TemplateIndex(DataBase database) {
        super(database);
    }

    public String getTypeFile() {
        return "html";
    }

    public String getBody() {
        ConfigCreateProject conf = ConfigCreateProject.getIstance();
        Map<String, Object> data = new HashMap<>();
        data.put("projectName", conf.getProjectName());
        return FreemarkerTemplate.process("fe/index.html.ftl", data);
    }

    public String getClassName() {
        return "index";
    }

    @Override
    public String getTypeTemplate() {
        String typeTemplate = "";
        return typeTemplate;
    }

    @Override
    public String getSourceFolder() {
        return "src/main/webapp";
    }

}
