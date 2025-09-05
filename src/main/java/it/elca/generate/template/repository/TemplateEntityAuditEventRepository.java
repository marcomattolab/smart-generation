package it.elca.generate.template.repository;

import it.elca.generate.ConfigCreateProject;
import it.elca.generate.DataBase;
import it.elca.generate.Utils;
import it.elca.generate.template.AbstractTemplate;
import it.elca.generate.template.FreemarkerTemplate;

import java.util.HashMap;
import java.util.Map;

public class TemplateEntityAuditEventRepository extends AbstractTemplate {

    public TemplateEntityAuditEventRepository(DataBase database) {
        super(database);
    }

    public String getTypeTemplate() {
        return Utils.replace(ConfigCreateProject.getIstance().getSrcRepositoryFolder(), ".", "/");
    }

    public String getTypeFile() {
        return "java";
    }

    public String getBody() {
        ConfigCreateProject conf = ConfigCreateProject.getIstance();
        Map<String, Object> data = new HashMap<>();
        data.put("packageClass", conf.getPackageclass());
        data.put("srcRepositoryFolder", conf.getSrcRepositoryFolder());
        data.put("srcDomainFolder", conf.getSrcDomainFolder());
        data.put("className", getClassName());
        return FreemarkerTemplate.process("repository/entity.audit.event.repository.java.ftl", data);
    }

    public String getClassName() {
        return "EntityAuditEventRepository";
    }

    public String getSourceFolder() {
        return "src/main/java";
    }

}
