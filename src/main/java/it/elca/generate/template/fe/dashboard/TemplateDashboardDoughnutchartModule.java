package it.elca.generate.template.fe.dashboard;

import it.elca.generate.ConfigCreateProject;
import it.elca.generate.DataBase;
import it.elca.generate.Utils;
import it.elca.generate.template.AbstractResourceTemplate;
import it.elca.generate.template.FreemarkerTemplate;

import java.util.HashMap;
import java.util.Map;

public class TemplateDashboardDoughnutchartModule extends AbstractResourceTemplate {

    public TemplateDashboardDoughnutchartModule(DataBase database) {
        super(database);
    }

    public String getTypeFile() {
        return "ts";
    }

    public String getBody() {
        ConfigCreateProject conf = ConfigCreateProject.getIstance();
        Map<String, Object> data = new HashMap<>();
        data.put("projectName", Utils.getClassNameCamelCase(conf.getProjectName()));
        return FreemarkerTemplate.process("fe/dashboard/doughnutchart.module.ts.ftl", data);
    }

    public String getClassName() {
        return "doughnutchart.module";
    }

    @Override
    public String getTypeTemplate() {
        String typeTemplate = "";
        return typeTemplate;
    }

    @Override
    public String getSourceFolder() {
        return "src/main/webapp/app/dashboard/doughnutchart";
    }

}
