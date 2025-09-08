package it.elca.generate.template.service;

import java.util.HashMap;
import java.util.Map;

import it.elca.generate.ConfigCreateProject;
import it.elca.generate.Table;
import it.elca.generate.Utils;
import it.elca.generate.template.AbstractTemplate;
import it.elca.generate.template.FreemarkerTemplate;

public class TemplateServiceImpl extends AbstractTemplate {

    public TemplateServiceImpl(Table tabella) {
        super(tabella);
    }

    @Override
    public String getBody() {
        ConfigCreateProject conf = ConfigCreateProject.getIstance();
        Map<String, Object> data = new HashMap<>();
        data.put("packageName", conf.getPackageclass());
        data.put("serviceImplPackage", conf.getSrcServiceImplFolder());
        data.put("servicePackage", conf.getSrcServiceFolder());
        data.put("domainPackage", conf.getSrcDomainFolder());
        data.put("repositoryPackage", conf.getSrcRepositoryFolder());
        data.put("dtoPackage", conf.getSrcServiceDtoFolder());
        data.put("mapperPackage", conf.getSrcServiceMapperFolder());
        data.put("entityName", Utils.getEntityName(tabella));
        data.put("entityFieldName", Utils.getFieldName(tabella));
        // FIXME => data.put("entityVarName", Utils.getEntityVarName(tabella));
        data.put("className", getClassName());
        data.put("serviceClassName", Utils.getServiceClassName(tabella));
        data.put("repositoryClassName", Utils.getRepositoryClassName(tabella));
        /**FIXME**/data.put("repositoryVarName", Utils.getRepositoryVarName(tabella));
        data.put("dtoClassName", Utils.getDTOClassName(tabella));
        // FIXME =>data.put("dtoVarName", Utils.getDTOVarName(tabella));
        data.put("mapperClassName", Utils.getMapperClassName(tabella));
        // FIXME =>data.put("mapperVarName", Utils.getMapperVarName(tabella));

        return FreemarkerTemplate.process("service/service-impl.java.ftl", data);
    }

    public String getClassName() {
        return Utils.getServiceImplClassName(tabella);
    }

    @Override
    public String getTypeTemplate() {
        return ConfigCreateProject.getIstance().getSrcServiceImplFolder().replace(".", "/");
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
