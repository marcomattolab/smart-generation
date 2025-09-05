package it.elca.generate.template.fe.dashboard;

import it.elca.generate.ConfigCreateProject;
import it.elca.generate.DataBase;
import it.elca.generate.Utils;
import it.elca.generate.template.AbstractResourceTemplate;
import it.elca.generate.template.FreemarkerTemplate;

import java.util.HashMap;
import java.util.Map;

public class TemplateDashboardPolarareachartRoute extends AbstractResourceTemplate {

    public TemplateDashboardPolarareachartRoute(DataBase database) {
        super(database);
    }

    public String getTypeFile() {
        return "ts";
    }

    public String getBody() {
        ConfigCreateProject conf = ConfigCreateProject.getIstance();
        Map<String, Object> data = new HashMap<>();
        data.put("authorities", Utils.getGlobalAuthorities(conf, Utils.APICE));
        return FreemarkerTemplate.process("fe/dashboard/polarareachart.route.ts.ftl", data);
    }

    public String getClassName() {
        return "polarareachart.route";
    }

    @Override
    public String getTypeTemplate() {
        String typeTemplate = "";
        return typeTemplate;
    }

    @Override
    public String getSourceFolder() {
        return "src/main/webapp/app/dashboard/polarareachart";
    }

}
