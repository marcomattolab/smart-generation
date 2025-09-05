package it.elca.generate.template.service;

import it.elca.generate.ConfigCreateProject;
import it.elca.generate.DataBase;
import it.elca.generate.template.AbstractTemplate;
import it.elca.generate.template.FreemarkerTemplate;

import java.util.HashMap;
import java.util.Map;

public class TemplateRandomUtil extends AbstractTemplate {

    public TemplateRandomUtil(DataBase database) {
        super(database);
    }

    @Override
    public String getBody() {
        ConfigCreateProject conf = ConfigCreateProject.getIstance();
        Map<String, Object> data = new HashMap<>();
        data.put("packageName", conf.getPackageclass());
        data.put("utilPackage", conf.getSrcServiceUtilFolder());
        return FreemarkerTemplate.process("service/RandomUtil.java.ftl", data);
    }

    public String getClassName() {
        return "RandomUtil";
    }

    @Override
    public String getTypeTemplate() {
        return ConfigCreateProject.getIstance().getSrcServiceUtilFolder().replace(".", "/");
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
