package it.elca.generate.template.conf;

import it.elca.generate.ConfigCreateProject;
import it.elca.generate.DataBase;
import it.elca.generate.Utils;
import it.elca.generate.template.AbstractTemplate;
import it.elca.generate.template.FreemarkerTemplate;

import java.util.HashMap;
import java.util.Map;

public class TemplateSecurityConfiguration extends AbstractTemplate {

    public TemplateSecurityConfiguration(DataBase database) {
        super(database);
    }

    public String getTypeFile() {
        return "java";
    }

    public String getBody() {
        ConfigCreateProject conf = ConfigCreateProject.getIstance();
        Map<String, Object> data = new HashMap<>();
        data.put("packageClass", conf.getPackageclass());
        data.put("srcConfigFolder", conf.getSrcConfigFolder());
        data.put("srcSecurityFolder", conf.getSrcSecurityFolder());
        data.put("srcSecurityJWTFolder", conf.getSrcSecurityJWTFolder());
        data.put("className", getClassName());
        return FreemarkerTemplate.process("conf/security.configuration.java.ftl", data);
    }

    public String getClassName() {
        return "SecurityConfiguration";
    }

    @Override
    public String getTypeTemplate() {
        return Utils.replace(ConfigCreateProject.getIstance().getSrcConfigFolder(), ".", "/");
    }

    @Override
    public String getSourceFolder() {
        return "src/main/java";
    }

}
