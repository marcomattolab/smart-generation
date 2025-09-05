package it.elca.generate.template.conf;

import it.elca.generate.ConfigCreateProject;
import it.elca.generate.DataBase;
import it.elca.generate.Utils;
import it.elca.generate.template.AbstractTemplate;
import it.elca.generate.template.FreemarkerTemplate;

import java.util.HashMap;
import java.util.Map;

public class TemplateLoggingAspectConfiguration extends AbstractTemplate {

    public TemplateLoggingAspectConfiguration(DataBase database) {
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
        data.put("srcAopLoggingFolder", conf.getSrcAopLoggingFolder());
        data.put("className", getClassName());
        return FreemarkerTemplate.process("conf/logging.aspect.configuration.java.ftl", data);
    }

    public String getClassName() {
        return "LoggingAspectConfiguration";
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
