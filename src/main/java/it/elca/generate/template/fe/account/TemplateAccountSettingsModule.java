package it.elca.generate.template.fe.account;

import it.elca.generate.ConfigCreateProject;
import it.elca.generate.DataBase;
import it.elca.generate.Utils;
import it.elca.generate.template.AbstractResourceTemplate;
import it.elca.generate.template.FreemarkerTemplate;

import java.util.HashMap;
import java.util.Map;

public class TemplateAccountSettingsModule extends AbstractResourceTemplate {

    public TemplateAccountSettingsModule(DataBase database) {
        super(database);
    }

    public String getTypeFile() {
        return "ts";
    }

    public String getBody() {
        ConfigCreateProject conf = ConfigCreateProject.getIstance();
        Map<String, Object> data = new HashMap<>();
        data.put("authorities", Utils.getGlobalAuthorities(conf, Utils.APICE));
        return FreemarkerTemplate.process("fe/account/settings.route.ts.ftl", data);
    }

    public String getClassName() {
        return "settings.route";
    }

    @Override
    public String getTypeTemplate() {
        String typeTemplate = "";
        return typeTemplate;
    }

    @Override
    public String getSourceFolder() {
        return "src/main/webapp/app/account/settings";
    }

}
