package it.elca.generate.template.web;

import it.elca.generate.ConfigCreateProject;
import it.elca.generate.DataBase;
import it.elca.generate.template.AbstractTemplate;
import it.elca.generate.template.FreemarkerTemplate;

import java.util.HashMap;
import java.util.Map;

public class TemplateEntityAuditResource extends AbstractTemplate {

    public TemplateEntityAuditResource(DataBase database) {
        super(database);
    }

    @Override
    public String getBody() {
        ConfigCreateProject conf = ConfigCreateProject.getIstance();
        Map<String, Object> data = new HashMap<>();
        data.put("packageName", conf.getPackageclass());
        data.put("webRestPackage", conf.getSrcWebRestFolder());
        data.put("domainPackage", conf.getSrcDomainFolder());
        data.put("repositoryPackage", conf.getSrcRepositoryFolder());
        data.put("securityPackage", conf.getSrcSecurityFolder());

        return FreemarkerTemplate.process("web/EntityAuditResource.java.ftl", data);
    }

    public String getClassName() {
        return "EntityAuditResource";
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
