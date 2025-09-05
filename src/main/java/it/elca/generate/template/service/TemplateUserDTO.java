package it.elca.generate.template.service;

import it.elca.generate.ConfigCreateProject;
import it.elca.generate.DataBase;
import it.elca.generate.template.AbstractTemplate;
import it.elca.generate.template.FreemarkerTemplate;

import java.util.HashMap;
import java.util.Map;

public class TemplateUserDTO extends AbstractTemplate {

    public TemplateUserDTO(DataBase database) {
        super(database);
    }

    @Override
    public String getBody() {
        ConfigCreateProject conf = ConfigCreateProject.getIstance();
        Map<String, Object> data = new HashMap<>();
        data.put("packageName", conf.getPackageclass());
        data.put("dtoPackage", conf.getSrcServiceDtoFolder());
        data.put("configPackage", conf.getSrcConfigFolder());
        data.put("domainPackage", conf.getSrcDomainFolder());
        return FreemarkerTemplate.process("service/UserDTO.java.ftl", data);
    }

    public String getClassName() {
        return "UserDTO";
    }

    @Override
    public String getTypeTemplate() {
        return ConfigCreateProject.getIstance().getSrcServiceDtoFolder().replace(".", "/");
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
