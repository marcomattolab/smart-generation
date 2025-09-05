package it.elca.generate.template.fe.blocks;

import it.elca.generate.ConfigCreateProject;
import it.elca.generate.DataBase;
import it.elca.generate.template.AbstractResourceTemplate;
import it.elca.generate.template.FreemarkerTemplate;

import java.util.HashMap;
import java.util.Map;

public class TemplateErrorehandlerInterceptor extends AbstractResourceTemplate {

    public TemplateErrorehandlerInterceptor(DataBase database) {
        super(database);
    }

    public String getTypeFile() {
        return "ts";
    }

    public String getBody() {
        ConfigCreateProject conf = ConfigCreateProject.getIstance();
        Map<String, Object> data = new HashMap<>();
        data.put("projectName", conf.getProjectName());
        return FreemarkerTemplate.process("fe/blocks/errorhandler.interceptor.ts.ftl", data);
    }

    public String getClassName() {
        return "errorhandler.interceptor";
    }

    @Override
    public String getTypeTemplate() {
        String typeTemplate = "";
        return typeTemplate;
    }

    @Override
    public String getSourceFolder() {
        return "src/main/webapp/app/blocks/interceptor";
    }

}
