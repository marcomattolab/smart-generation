package it.elca.generate.template.domain;

import it.elca.generate.ConfigCreateProject;
import it.elca.generate.DataBase;
import it.elca.generate.Utils;
import it.elca.generate.template.AbstractTemplate;
import it.elca.generate.template.FreemarkerTemplate;

import java.util.HashMap;
import java.util.Map;

public class TemplateUser extends AbstractTemplate {

    public TemplateUser(DataBase database) {
        super(database);
    }

    public String getTypeFile() {
        return "java";
    }

    public String getBody() {
        ConfigCreateProject conf = ConfigCreateProject.getIstance();
        Map<String, Object> data = new HashMap<>();
        data.put("packageClass", conf.getPackageclass());
        data.put("srcDomainFolder", conf.getSrcDomainFolder());
        data.put("srcConfigFolder", conf.getSrcConfigFolder());
        data.put("className", getClassName());
        return FreemarkerTemplate.process("domain/user.java.ftl", data);
    }

    public String getClassName() {
        return "User";
    }

    @Override
    public String getTypeTemplate() {
        return Utils.replace(ConfigCreateProject.getIstance().getSrcDomainFolder(), ".", "/");
    }

    @Override
    public String getSourceFolder() {
        return "src/main/java";
    }

}
