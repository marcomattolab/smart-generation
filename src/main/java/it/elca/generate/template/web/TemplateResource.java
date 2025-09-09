package it.elca.generate.template.web;

import it.elca.generate.ConfigCreateProject;
import it.elca.generate.Table;
import it.elca.generate.Utils;
import it.elca.generate.template.AbstractTemplate;
import it.elca.generate.template.FreemarkerTemplate;

import java.util.HashMap;
import java.util.Map;

public class TemplateResource extends AbstractTemplate {

    public TemplateResource(Table tabella) {
        super(tabella);
    }

    @Override
    public String getBody() {
        ConfigCreateProject conf = ConfigCreateProject.getIstance();
        Map<String, Object> data = new HashMap<>();
        data.put("packageName", conf.getPackageclass());
        data.put("webRestPackage", conf.getSrcWebRestFolder());
        data.put("errorsPackage", conf.getSrcWebRestErrorsFolder());
        data.put("utilPackage", conf.getSrcWebRestUtilFolder());
        data.put("dtoPackage", conf.getSrcServiceDtoFolder());
        data.put("servicePackage", conf.getSrcServiceFolder());
        data.put("entityName", Utils.getEntityName(tabella));
        data.put("entityFieldName", Utils.getFieldName(tabella));
        data.put("className", getClassName());
        data.put("dtoClassName", Utils.getDTOClassName(tabella));
        data.put("criteriaClassName", Utils.getCriteriaClassName(tabella));
        data.put("serviceClassName", Utils.getServiceClassName(tabella));
        data.put("queryServiceClassName", Utils.getQueryServiceClassName(tabella));
        data.put("restName", Utils.getRestName(tabella));
        data.put("dtoVarName", Utils.getDTOVarName(tabella));
        data.put("serviceVarName", Utils.getServiceVarName(tabella));
        data.put("queryServiceVarName", Utils.getQueryServiceVarName(tabella));

        return FreemarkerTemplate.process("web/resource.java.ftl", data);
    }

    public String getClassName() {
        return Utils.getResourceClassName(tabella);
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
