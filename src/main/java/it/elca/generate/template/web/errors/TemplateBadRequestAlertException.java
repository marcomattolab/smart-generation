package it.elca.generate.template.web.errors;

import it.elca.generate.ConfigCreateProject;
import it.elca.generate.DataBase;
import it.elca.generate.template.AbstractTemplate;
import it.elca.generate.template.FreemarkerTemplate;

import java.util.HashMap;
import java.util.Map;

public class TemplateBadRequestAlertException extends AbstractTemplate {

    public TemplateBadRequestAlertException(DataBase dataBase) {
        super(dataBase);
    }

    @Override
    public String getBody() {
        ConfigCreateProject conf = ConfigCreateProject.getIstance();
        Map<String, Object> data = new HashMap<>();
        data.put("packageName", conf.getPackageclass());
        data.put("errorsPackage", conf.getSrcWebRestErrorsFolder());
        return FreemarkerTemplate.process("web/errors/BadRequestAlertException.java.ftl", data);
    }

    public String getClassName() {
        return "BadRequestAlertException";
    }

    @Override
    public String getTypeTemplate() {
        return ConfigCreateProject.getIstance().getSrcWebRestErrorsFolder().replace(".", "/");
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
