package it.elca.generate.template.repository;

import it.elca.generate.ConfigCreateProject;
import it.elca.generate.Table;
import it.elca.generate.Utils;
import it.elca.generate.template.AbstractTemplate;
import it.elca.generate.template.FreemarkerTemplate;

import java.util.HashMap;
import java.util.Map;

public class TemplateRepository extends AbstractTemplate {

    public TemplateRepository(Table tabella) {
        super(tabella);
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
        data.put("entityName", Utils.getEntityName(tabella));
        data.put("className", getClassName());
        return FreemarkerTemplate.process("repository/repository.java.ftl", data);
    }

    public String getClassName() {
        return Utils.getRepositoryClassName(tabella);
    }

    public String getSourceFolder() {
        return "src/main/java";
    }

}
