package it.elca.generate.template.service;

import it.elca.generate.ConfigCreateProject;
import it.elca.generate.Table;
import it.elca.generate.Utils;
import it.elca.generate.template.AbstractTemplate;
import it.elca.generate.template.FreemarkerTemplate;

import java.util.HashMap;
import java.util.Map;

public class TemplateService extends AbstractTemplate {

    public TemplateService(Table tabella) {
        super(tabella);
    }

    @Override
    public String getBody() {
        ConfigCreateProject conf = ConfigCreateProject.getIstance();
        Map<String, Object> data = new HashMap<>();
        data.put("packageName", conf.getPackageclass());
        data.put("servicePackage", conf.getSrcServiceFolder());
        data.put("dtoPackage", conf.getSrcServiceDtoFolder());
        data.put("entityName", Utils.getEntityName(tabella));
        data.put("entityFieldName", Utils.getFieldName(tabella));
        data.put("className", getClassName());
        return FreemarkerTemplate.process("service/service.java.ftl", data);
    }

    public String getClassName() {
        return Utils.getServiceClassName(tabella);
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
