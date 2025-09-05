package it.elca.generate.template.service;

import it.elca.generate.ConfigCreateProject;
import it.elca.generate.DataBase;
import it.elca.generate.template.AbstractTemplate;
import it.elca.generate.template.FreemarkerTemplate;

import java.util.HashMap;
import java.util.Map;

public class TemplateMailService extends AbstractTemplate {

    public TemplateMailService(DataBase database) {
        super(database);
    }

    @Override
    public String getBody() {
        ConfigCreateProject conf = ConfigCreateProject.getIstance();
        Map<String, Object> data = new HashMap<>();
        data.put("packageName", conf.getPackageclass());
        data.put("servicePackage", conf.getSrcServiceFolder());
        data.put("domainPackage", conf.getSrcDomainFolder());
        return FreemarkerTemplate.process("service/MailService.java.ftl", data);
    }

    public String getClassName() {
        return "MailService";
    }

    @Override
    public String getTypeTemplate() {
        return ConfigCreateProject.getIstance().getSrcServiceFolder().replace(".", "/");
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
