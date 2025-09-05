package it.elca.generate.template.fe;

import it.elca.generate.ConfigCreateProject;
import it.elca.generate.DataBase;
import it.elca.generate.Utils;
import it.elca.generate.template.AbstractResourceTemplate;
import it.elca.generate.template.FreemarkerTemplate;

import java.util.HashMap;
import java.util.Map;

public class TemplateManifest extends AbstractResourceTemplate {

    public TemplateManifest(DataBase database) {
        super(database);
    }

    public String getTypeFile() {
        return "webapp";
    }

    public String getBody() {
        ConfigCreateProject conf = ConfigCreateProject.getIstance();
        Map<String, Object> data = new HashMap<>();
        data.put("projectName", Utils.getClassNameCamelCase(conf.getProjectName()));
        return FreemarkerTemplate.process("fe/manifest.webapp.ftl", data);
    }

    public String getClassName() {
        return "manifest";
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
