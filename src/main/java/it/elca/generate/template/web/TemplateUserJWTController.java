package it.elca.generate.template.web;

import it.elca.generate.ConfigCreateProject;
import it.elca.generate.DataBase;
import it.elca.generate.template.AbstractTemplate;
import it.elca.generate.template.FreemarkerTemplate;

import java.util.HashMap;
import java.util.Map;

public class TemplateUserJWTController extends AbstractTemplate {

    public TemplateUserJWTController(DataBase database) {
        super(database);
    }

    @Override
    public String getBody() {
        ConfigCreateProject conf = ConfigCreateProject.getIstance();
        Map<String, Object> data = new HashMap<>();
        data.put("packageName", conf.getPackageclass());
        data.put("webRestPackage", conf.getSrcWebRestFolder());
        data.put("securityJwtPackage", conf.getSrcSecurityJwtFolder());
        data.put("vmPackage", conf.getSrcWebRestVmFolder());

        return FreemarkerTemplate.process("web/UserJWTController.java.ftl", data);
    }

    public String getClassName() {
        return "UserJWTController";
    }

    @Override
    public String getTypeTemplate() {
        return ConfigCreateProject.getIstance().getSrcWebRestFolder().replace(".", "/");
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
