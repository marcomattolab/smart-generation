package it.elca.generate.template.conf.audit;

import it.elca.generate.ConfigCreateProject;
import it.elca.generate.DataBase;
import it.elca.generate.Utils;
import it.elca.generate.template.AbstractTemplate;
import it.elca.generate.template.FreemarkerTemplate;

import java.util.HashMap;
import java.util.Map;

public class TemplateAsyncEntityAuditEventWriter extends AbstractTemplate {

    public TemplateAsyncEntityAuditEventWriter(DataBase database) {
        super(database);
    }

    public String getTypeFile() {
        return "java";
    }

    public String getBody() {
        ConfigCreateProject conf = ConfigCreateProject.getIstance();
        Map<String, Object> data = new HashMap<>();
        data.put("packageClass", conf.getPackageclass());
        data.put("srcConfigAuditFolder", conf.getSrcConfigAuditFolder());
        data.put("srcDomainFolder", conf.getSrcDomainFolder());
        data.put("srcRepositoryFolder", conf.getSrcRepositoryFolder());
        data.put("className", getClassName());
        return FreemarkerTemplate.process("conf/audit/async.entity.audit.event.writer.java.ftl", data);
    }

    public String getClassName() {
        return "AsyncEntityAuditEventWriter";
    }

    @Override
    public String getTypeTemplate() {
        return Utils.replace(ConfigCreateProject.getIstance().getSrcConfigAuditFolder(), ".", "/");
    }

    @Override
    public String getSourceFolder() {
        return "src/main/java";
    }

}
