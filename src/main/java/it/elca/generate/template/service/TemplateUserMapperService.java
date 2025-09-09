package it.elca.generate.template.service;

import it.elca.generate.ConfigCreateProject;
import it.elca.generate.DataBase;
import it.elca.generate.template.AbstractTemplate;
import it.elca.generate.template.FreemarkerTemplate;

import java.util.HashMap;
import java.util.Map;

public class TemplateUserMapperService extends AbstractTemplate {

    public TemplateUserMapperService(DataBase database) {
        super(database);
    }

    @Override
    public String getBody() {
        ConfigCreateProject conf = ConfigCreateProject.getIstance();
        Map<String, Object> data = new HashMap<>();
        data.put("packageName", conf.getPackageclass());
        data.put("mapperPackage", conf.getSrcServiceMapperFolder());
        data.put("domainPackage", conf.getSrcDomainFolder());
        data.put("dtoPackage", conf.getSrcServiceDtoFolder());
        return FreemarkerTemplate.process("service/UserMapper.java.ftl", data);
    }

    public String getClassName() {
        return "UserMapper";
    }

    @Override
    public String getTypeTemplate() {
        return ConfigCreateProject.getIstance().getSrcServiceMapperFolder().replace(".", "/");
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
