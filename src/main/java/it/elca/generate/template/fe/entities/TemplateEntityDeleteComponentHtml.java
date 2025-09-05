package it.elca.generate.template.fe.entities;

import it.elca.generate.ConfigCreateProject;
import it.elca.generate.Table;
import it.elca.generate.Utils;
import it.elca.generate.template.AbstractResourceTemplate;
import it.elca.generate.template.FreemarkerTemplate;

import java.util.HashMap;
import java.util.Map;

public class TemplateEntityDeleteComponentHtml extends AbstractResourceTemplate {

    public TemplateEntityDeleteComponentHtml(Table tabella) {
        super(tabella);
    }

    public String getTypeFile() {
        return "html";
    }

    public String getBody() {
        ConfigCreateProject conf = ConfigCreateProject.getIstance();
        Map<String, Object> data = new HashMap<>();
        data.put("projectName", conf.getProjectName());
        data.put("nometabella", Utils.getClassNameLowerCase(tabella));
        data.put("Nometabella", Utils.getEntityName(tabella));
        return FreemarkerTemplate.process("fe/entities/entity-delete-dialog.component.html.ftl", data);
    }

    public String getClassName() {
        return Utils.getClassNameLowerCase(tabella) + "-delete-dialog.component";
    }

    @Override
    public String getTypeTemplate() {
        String typeTemplate = "";
        return typeTemplate;
    }

    @Override
    public String getSourceFolder() {
        return "src/main/webapp/app/entities/" + Utils.getClassNameLowerCase(tabella);
    }

}
