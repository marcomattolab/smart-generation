package it.elca.generate.template.web;

import it.elca.generate.ConfigCreateProject;
import it.elca.generate.DataBase;
import it.elca.generate.template.AbstractTemplate;
import it.elca.generate.template.FreemarkerTemplate;

import java.util.HashMap;
import java.util.Map;

public class TemplateUserResource extends AbstractTemplate {

    public TemplateUserResource(DataBase database) {
        super(database);
    }

    @Override
    public String getBody() {
        ConfigCreateProject conf = ConfigCreateProject.getIstance();
        Map<String, Object> data = new HashMap<>();
        data.put("packageName", conf.getPackageclass());
        data.put("webRestPackage", conf.getSrcWebRestFolder());
        data.put("configPackage", conf.getSrcConfigFolder());
        data.put("domainPackage", conf.getSrcDomainFolder());
        data.put("repositoryPackage", conf.getSrcRepositoryFolder());
        data.put("securityPackage", conf.getSrcSecurityFolder());
        data.put("servicePackage", conf.getSrcServiceFolder());
        data.put("dtoPackage", conf.getSrcServiceDtoFolder());
        data.put("errorsPackage", conf.getSrcWebRestErrorsFolder());
        data.put("utilPackage", conf.getSrcWebRestUtilFolder());

        return FreemarkerTemplate.process("web/UserResource.java.ftl", data);
    }

    public String getClassName() {
        return "UserResource";
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
