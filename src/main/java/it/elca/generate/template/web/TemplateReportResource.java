package it.elca.generate.template.web;

import it.elca.generate.ConfigCreateProject;
import it.elca.generate.Table;
import it.elca.generate.Utils;
import it.elca.generate.template.AbstractTemplate;
import it.elca.generate.template.FreemarkerTemplate;

import java.util.HashMap;
import java.util.Map;

public class TemplateReportResource extends AbstractTemplate {

    public TemplateReportResource(Table tabella) {
        super(tabella);
    }

    @Override
    public String getBody() {
        ConfigCreateProject conf = ConfigCreateProject.getIstance();
        Map<String, Object> data = new HashMap<>();
        data.put("packageName", conf.getPackageclass());
        data.put("webRestPackage", conf.getSrcWebRestFolder());
        data.put("className", getClassName());
        data.put("entityFieldName", Utils.getFieldName(tabella));

        return FreemarkerTemplate.process("web/ReportResource.java.ftl", data);
    }

    public String getClassName() {
        return Utils.getEntityName(tabella) + "ReportResource";
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
