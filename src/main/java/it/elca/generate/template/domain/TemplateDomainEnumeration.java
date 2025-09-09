package it.elca.generate.template.domain;

import it.elca.generate.ConfigCreateProject;
import it.elca.generate.Enumeration;
import it.elca.generate.Utils;
import it.elca.generate.template.AbstractTemplate;
import it.elca.generate.template.FreemarkerTemplate;

import java.util.HashMap;
import java.util.Map;

public class TemplateDomainEnumeration extends AbstractTemplate {

    public TemplateDomainEnumeration(Enumeration enumeration) {
        super(enumeration);
    }

    public String getTypeTemplate() {
        return Utils.replace(ConfigCreateProject.getIstance().getSrcDomainEnumerationFolder(),".","/");
    }

    public String getTypeFile() {
        return "java";
    }

    public String getBody() {
        ConfigCreateProject conf = ConfigCreateProject.getIstance();
        Map<String, Object> data = new HashMap<>();
        data.put("packageClass", conf.getPackageclass());
        data.put("srcDomainEnumerationFolder", conf.getSrcDomainEnumerationFolder());
        data.put("enumerationName", enumeration.getNomeEnumeration());
        data.put("values", enumeration.getValoriEnumeration());
        return FreemarkerTemplate.process("domain/domain.enumeration.java.ftl", data);
    }

    public String getClassName() {
        return enumeration.getNomeEnumeration();
    }

    public String getSourceFolder() {
        return "src/main/java";
    }

}
