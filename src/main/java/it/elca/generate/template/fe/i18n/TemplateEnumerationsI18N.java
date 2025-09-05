package it.elca.generate.template.fe.i18n;

import it.elca.generate.ConfigCreateProject;
import it.elca.generate.Enumeration;
import it.elca.generate.template.AbstractResourceTemplate;
import it.elca.generate.template.FreemarkerTemplate;

import java.util.HashMap;
import java.util.Map;

public class TemplateEnumerationsI18N extends AbstractResourceTemplate {
    private String languageCode;

    public TemplateEnumerationsI18N(Enumeration enumeration, String languageCode) {
        super(enumeration);
        this.languageCode = languageCode;
    }

    @Override
    public String getBody() {
        ConfigCreateProject conf = ConfigCreateProject.getIstance();
        Map<String, Object> data = new HashMap<>();
        data.put("projectName", conf.getProjectName());
        data.put("enumerationName", enumeration.getNomeEnumeration());
        data.put("values", enumeration.getValoriEnumeration());
        return FreemarkerTemplate.process("fe/i18n/enumeration.json.ftl", data);
    }

    public String getClassName() {
        return enumeration.getNomeEnumeration().toLowerCase();
    }

    @Override
    public String getTypeFile() {
        return "json";
    }

    @Override
    public String getTypeTemplate() {
        return "";
    }

    @Override
    public String getSourceFolder() {
        return "src/main/webapp/i18n/" + languageCode;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }
}
